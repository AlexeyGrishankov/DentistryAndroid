package ru.icomplex.dentistry.di.modules

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.*
import ru.icomplex.dentistry.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.icomplex.dentistry.Constant
import ru.icomplex.dentistry.extension.toast
import ru.icomplex.dentistry.model.auth.RetrofitAuth
import ru.icomplex.dentistry.model.settings.AppSettings
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
class NetworkModule {

    companion object {
        private const val MAX_CACHE_SIZE_10_MB: Long = 10 * 1024 * 1024
        const val CACHE_CONTROL_HEADER = "Cache-Control"
        const val CACHE_CONTROL_NO_CACHE = "no-cache"
        val CACHE_CONTROL_MIN =
            CacheControl.Builder().maxAge(10, TimeUnit.SECONDS).build().toString()
        val CACHE_CONTROL_MAX =
            CacheControl.Builder().maxAge(30, TimeUnit.SECONDS).build().toString()
    }

    @[Provides Singleton]
    fun provideGson(): Gson {
        return GsonBuilder()
            .setPrettyPrinting()
            .create()
    }

    @[Provides Singleton]
    fun provideClient(
        @ApplicationContext appContext: Context,
        settings: AppSettings,
        retrofitAuth: RetrofitAuth,
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.callTimeout(60, TimeUnit.SECONDS)

        val cache = Cache(appContext.cacheDir, MAX_CACHE_SIZE_10_MB)
        httpClient.cache(cache)

        httpClient.addNetworkInterceptor(createCacheInterceptor())
        httpClient.addInterceptor(createAuthInterceptor(settings))
        httpClient.addInterceptor(createTrowInterceptor(appContext))
//        httpClient.authenticator(createAuthenticator(appContext, settings, retrofitAuth))
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(createLoggingInterceptor())
        }
        return httpClient.build()
    }

    @[Provides Singleton]
    fun provideRetrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BACKEND_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @[Provides Singleton]
    fun provideAuthRetrofit(gson: Gson, settings: AppSettings): RetrofitAuth {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(createLoggingInterceptor())
            .addInterceptor(createAuthInterceptor(settings))
            .build()
        return RetrofitAuth(
            Retrofit.Builder()
                .baseUrl(Constant.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        )
    }

    private fun createAuthInterceptor(settings: AppSettings): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            val token = settings.getCurrentToken()
            if (token != null) {
                newBuilder.addHeader("Authorization", "Bearer $token")
            }
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun createTrowInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val code = response.code
            if (code in 400..500) {
                response.body?.let { body ->
                    if (body.contentType() != null && body.contentType()!!.subtype.lowercase() == "json") {
                        try {
                            val source = body.source()
                            val buffer = source.buffer
                            body.contentType()!!.charset(Charset.forName("UTF-8"))?.let { charset ->
                                val rawJson = buffer.clone().readString(charset)
                                val jsonElement = JsonParser.parseString(rawJson)
                                if (jsonElement is JsonObject) {
                                    when {
                                        jsonElement.has("message") -> {
                                            val message = jsonElement["message"].asString
                                            runBlocking(Dispatchers.Main) {
                                                context.toast(message, Toast.LENGTH_LONG)
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (t: Throwable) {
                            t.printStackTrace()
                        }
                    }
                }
            }
            return@Interceptor response
        }
    }

    private fun createCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val originalResponse = chain.proceed(request)

            val shouldUseCache = request.header(CACHE_CONTROL_HEADER) != CACHE_CONTROL_NO_CACHE
            if (!shouldUseCache) return@Interceptor originalResponse

            val hasCacheMin = request.header(CACHE_CONTROL_HEADER) == CACHE_CONTROL_MIN
            if (hasCacheMin) {
                return@Interceptor originalResponse.newBuilder()
                    .header(CACHE_CONTROL_HEADER, CACHE_CONTROL_MIN)
                    .build()
            }

            return@Interceptor originalResponse.newBuilder()
                .header(CACHE_CONTROL_HEADER, CACHE_CONTROL_MAX)
                .build()
        }
    }

//    private fun createAuthenticator(
//        appContext: Context,
//        settings: AppSettings,
//        retrofitAuth: RetrofitAuth,
//    ): Authenticator {
//        return Authenticator { _, response ->
//            val newToken: String? =
//                retrofitAuth.retrofit.create(AuthApi::class.java)
//                    .refreshToken()
//                    .execute()
//                    .body()?.token
//
//            if (newToken == null) {
//                settings.setCurrentToken(null)
//                val intent = Intent(appContext, LoginActivity::class.java)
//                appContext.startActivity(intent)
//                return@Authenticator null
//            }
//
//            return@Authenticator response.request.newBuilder()
//                .header("Authorization", "Bearer $newToken")
//                .build()
//        }
//    }
}