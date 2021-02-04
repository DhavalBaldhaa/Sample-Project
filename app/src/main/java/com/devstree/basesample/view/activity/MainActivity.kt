package com.devstree.basesample.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devstree.basesample.R
import com.devstree.basesample.databinding.ActivityMainBinding
import com.devstree.basesample.view.base.NavigationActivity

class MainActivity : NavigationActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initUi() {
        setUpToolBar("Home", true)
    }
}