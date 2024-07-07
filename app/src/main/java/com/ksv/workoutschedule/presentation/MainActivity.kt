package com.ksv.workoutschedule.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.ksv.workoutschedule.R
import com.ksv.workoutschedule.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportFragmentManager.addOnBackStackChangedListener {
            Log.d("ksvlog", "OnBackStackChangedListener")

            var fragment = supportFragmentManager.findFragmentByTag("HISTORY_FRAG")
            if (fragment != null) {
                if (fragment.isVisible) {
//                        Toast.makeText(this@MainActivity, "HISTORY_FRAG", Toast.LENGTH_LONG).show()
                    Log.d("ksvlog", "HISTORY_FRAG")
                }
            }

            fragment = supportFragmentManager.findFragmentByTag("SETTING_FRAG")
            if (fragment != null) {
                if (fragment.isVisible) {
//                        Toast.makeText(this@MainActivity, "SETTING_FRAG", Toast.LENGTH_LONG).show()
                    Log.d("ksvlog", "SETTING_FRAG")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mMenu = menu

        menuInflater.inflate(R.menu.menu_main, menu)
//        val menuItem = menu?.findItem(R.id.menu_history)
//        menuItem?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // TODO сделать нормально через Навигацию
            R.id.menu_settings -> {
//                mMenu?.findItem(R.id.menu_history)?.isVisible = true
                val fr = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (fr !is SettingsFragment) {
                    supportFragmentManager.commit {
                        replace<SettingsFragment>(binding.fragmentContainer.id, "SETTING_FRAG")
                        addToBackStack(SettingsFragment::javaClass.name)
                    }
                }
                true
            }

            R.id.menu_history -> {
//                supportFragmentManager.popBackStack()
                val fr = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (fr !is HistoryFragment) {
                    supportFragmentManager.commit {
                        replace<HistoryFragment>(binding.fragmentContainer.id, "HISTORY_FRAG")
                        addToBackStack(HistoryFragment::javaClass.name)
                    }
                }
                item.isVisible = false
//                mMenu?.findItem(R.id.menu_history)?.isVisible = false
//                Log.d("ksvlog", "${supportFragmentManager.backStackEntryCount}")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}