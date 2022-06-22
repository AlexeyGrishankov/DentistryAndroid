package ru.icomplex.dentistry.iu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.icomplex.dentistry.extension.collectLiveData
import ru.icomplex.dentistry.extension.launch
import ru.icomplex.dentistry.extension.toLiveData
import ru.icomplex.dentistry.model.auth.AuthCode
import ru.icomplex.dentistry.model.auth.AuthGrant
import ru.icomplex.dentistry.model.auth.AuthPhone
import ru.icomplex.dentistry.repositories.auth.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _progress = MutableLiveData<Boolean>()
    val progress = _progress.toLiveData()

    private val _code = MutableLiveData<AuthCode>()
    val code = _code.toLiveData()

    private val _auth = MutableLiveData<AuthGrant>()
    val auth = _auth.toLiveData()

    fun sendSMS(phone: AuthPhone) {
        launch {
            authRepository.getAuthCode(phone).collectLiveData(_code)
        }
    }

    fun signIn(code: AuthCode) {
        launch(_progress) {
            authRepository.signIn(code).collectLiveData(_auth)
        }
    }

}