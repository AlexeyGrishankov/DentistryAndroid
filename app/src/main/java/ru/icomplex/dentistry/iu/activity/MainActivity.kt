package ru.icomplex.dentistry.iu.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.ActivityMainBinding
import ru.icomplex.dentistry.model.settings.AppSettings
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val bind by viewBinding(ActivityMainBinding::bind)

    @Inject
    lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (appSettings.getCurrentToken() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setContentView(bind.root)
        setSupportActionBar(bind.appBarMain.actionBar)

        val drawerLayout: DrawerLayout = bind.drawerLayout
        val navView: NavigationView = bind.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.fragmentDoctorList, R.id.fragmentServices, R.id.fragmentUserProfile),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}