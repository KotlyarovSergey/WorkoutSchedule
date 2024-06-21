package com.ksv.workoutschedule.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.ksv.workoutschedule.R
import com.ksv.workoutschedule.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mMenu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // TODO сделать нормально через Навигацию
            R.id.menu_settings -> {
                val fr = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if(fr !is SettingsFragment){
                    supportFragmentManager.commit {
                        replace<SettingsFragment>(binding.fragmentContainer.id)
                        addToBackStack(SettingsFragment::javaClass.name)
                    }
                }
                true
            }

            R.id.menu_history -> {
//                supportFragmentManager.popBackStack()
                val fr = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if(fr !is HistoryFragment){
                    supportFragmentManager.commit {
                        replace<HistoryFragment>(binding.fragmentContainer.id)
                        addToBackStack(HistoryFragment::javaClass.name)
                    }
                }
                Log.d("ksvlog", "${supportFragmentManager.backStackEntryCount}")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}