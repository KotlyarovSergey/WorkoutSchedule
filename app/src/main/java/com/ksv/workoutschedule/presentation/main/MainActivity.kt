package com.ksv.workoutschedule.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.forEach
import androidx.core.view.iterator
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.ksv.workoutschedule.R
import com.ksv.workoutschedule.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        hideMenuItem(menu)

        supportFragmentManager.addOnBackStackChangedListener {
            hideMenuItem(menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun hideMenuItem(menu: Menu?) {
        // visible all items
        menu?.forEach { item ->
            item.isVisible = true
        }

        // get current fragment and hide respective item
        val frag = supportFragmentManager.findFragmentById(R.id.fragment_container)
        when (frag) {
            is HistoryFragment -> {
                menu?.findItem(R.id.menu_history)?.isVisible = false
            }
//            is GreetingFragment -> {
//                menu?.findItem(R.id.menu_clear_history)?.isVisible = false
//            }
            is SettingsFragment -> {
                //menu?.findItem(R.id.menu_setting)?.isVisible = false
                menu?.forEach { item ->
                    item.isVisible = false
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        return when (item.itemId) {
            R.id.menu_history -> {
                if (fragment !is HistoryFragment) {
                    supportFragmentManager.commit {
                        replace<HistoryFragment>(binding.fragmentContainer.id, HistoryFragment::class.java.name)
                        addToBackStack(HistoryFragment::class.java.name)
                    }
                }
                true
            }
            R.id.menu_setting -> {
                if (fragment !is SettingsFragment) {
                    supportFragmentManager.commit {
                        replace<SettingsFragment>(binding.fragmentContainer.id, SettingsFragment::class.java.name)
                        addToBackStack(SettingsFragment::class.java.name)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}