package com.satoruchannel.barberinhomebarbeiro

import android.app.FragmentTransaction
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var calendarFragment: CalendarFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var aboutFragment: AboutFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_calendar -> {
                setFragment(calendarFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                setFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                setFragment(aboutFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.mainFrame, fragment).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarFragment = CalendarFragment()
        profileFragment = ProfileFragment()
        aboutFragment = AboutFragment()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setFragment(calendarFragment)
    }
}
