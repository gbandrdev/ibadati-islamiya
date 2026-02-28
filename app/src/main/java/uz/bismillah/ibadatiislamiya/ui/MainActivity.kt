package uz.bismillah.ibadatiislamiya.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.ui.unit.UnitFragment.Companion.CURRENT_THEME

class MainActivity : AppCompatActivity() {
    companion object {
        const val TEXT_SIZE = "textSize"
    }

    private lateinit var preferences: SharedPreferences
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
//        TODO: AppCompatDelegate.setDefaultNightMode(preferences.getInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_NO))

        setupViews()

    }

    private fun setupViews() {
        var navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentNavHost) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.unitFragment,
                R.id.bookmarksFragment,
                R.id.searchFragment,
                R.id.infoFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentNavHost)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}