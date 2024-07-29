package com.ksv.workoutschedule.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
        val iterator = menu?.iterator()
        if (iterator != null) {
            for (item in iterator) {
                item.isVisible = true
            }
        }
        // get current fragment and hide respective item
        val frag = supportFragmentManager.findFragmentById(R.id.fragment_container)
        when (frag) {
            is HistoryFragment -> {
                menu?.findItem(R.id.menu_history)?.isVisible = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (fragment !is HistoryFragment) {
                    supportFragmentManager.commit {
                        replace<HistoryFragment>(binding.fragmentContainer.id, HistoryFragment::class.java.name)
                        addToBackStack(HistoryFragment::javaClass.name)
                    }
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}