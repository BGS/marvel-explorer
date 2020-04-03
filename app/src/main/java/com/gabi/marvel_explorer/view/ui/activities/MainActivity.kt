package com.gabi.marvel_explorer.view.ui.activities

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gabi.marvel_explorer.R
import com.gabi.marvel_explorer.utils.FirebaseAuthUtil
import com.gabi.marvel_explorer.view.ui.fragments.UserProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainActivity : AppCompatActivity() {
    private lateinit var userButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val params = Toolbar.LayoutParams(100, 100, Gravity.END)
        params.setMargins(0, 0, 30, 0)
        userButton = Button(this)


        userButton.backgroundDrawable = getResources().getDrawable(R.drawable.avatar_placeholder)
        toolbar.addView(userButton, params)


        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.main_nav_fragment))

        userButton.setOnClickListener { v ->
            if (FirebaseAuthUtil.currentUser == null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                findNavController(R.id.main_nav_fragment).navigate(R.id.action_comicListFragment_to_userProfileFragment)

            }

        }

    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_fragment).navigateUp()
}