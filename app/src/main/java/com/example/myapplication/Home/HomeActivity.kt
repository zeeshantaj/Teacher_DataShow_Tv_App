package com.example.myapplication.Home

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.BrowseFrameLayout
import com.example.myapplication.R
import com.google.android.gms.common.internal.service.Common

class HomeActivity : FragmentActivity(), View.OnKeyListener {

    lateinit var btnHome : TextView
    lateinit var btnKeySet : TextView
    lateinit var btnclassScrollTime : TextView
    lateinit var btnannounceScrollTime : TextView
    lateinit var navBar : BrowseFrameLayout
    var SIDE_MENU = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)

        btnHome = findViewById(R.id.homeBtn)
        btnKeySet = findViewById(R.id.keySetBtn)
        btnclassScrollTime = findViewById(R.id.classScrollTimeBtn)
        btnannounceScrollTime = findViewById(R.id.announceScrollTimeBtn)
        navBar = findViewById(R.id.navBar)

        btnHome.setOnKeyListener(this)
        btnKeySet.setOnKeyListener(this)
        btnclassScrollTime.setOnKeyListener(this)
        btnannounceScrollTime.setOnKeyListener(this)

        changeFragment(HomeFragment())
    }

    fun changeFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer,fragment)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && SIDE_MENU){
            SIDE_MENU = false
            closeMenu()
        }

        return super.onKeyDown(keyCode, event)

    }
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode){
            KeyEvent.KEYCODE_DPAD_LEFT ->{
                if (!SIDE_MENU){
                    openMenu()
                    SIDE_MENU = true
                }
            }
        }
        return false
    }

    private fun openMenu() {
        val params = navBar.layoutParams as ViewGroup.LayoutParams
        params.width = getWidth(16)
        navBar.layoutParams = params
    }

    private fun closeMenu() {
        val params = navBar.layoutParams as ViewGroup.LayoutParams
        params.width = getWidth(5)
        navBar.layoutParams = params
    }

    fun getWidth(percent: Int): Int{
        val width = resources.displayMetrics.widthPixels ?: 0
        return (width * percent) / 100
    }
}