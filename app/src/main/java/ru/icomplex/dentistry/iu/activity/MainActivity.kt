package ru.icomplex.dentistry.iu.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.ActivityMainBinding
import ru.icomplex.dentistry.extension.changeVisible

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val bind by viewBinding(ActivityMainBinding::bind)
    private var isHiddenToolbar = false

    //        private val bindDrawer by viewBinding(ActivityMainDrawerBinding::bind)
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        start()
    }

    private fun start() {
        setSupportActionBar(bind.actionBar)
        setNavigation()
    }

    private fun setNavigation() {
//        appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.fragmentDoctorList,
//            R.id.fragmentServices,
//            R.id.fragmentUserProfile,
//        ).setOpenableLayout(bindDrawer.drawerLayout).build()
        val host =
            supportFragmentManager.findFragmentById(R.id.navigationController) as NavHostFragment
        navController = host.navController
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        bindDrawer.navView.setupWithNavController(navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
}
