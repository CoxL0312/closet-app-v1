package com.example.closetapp_v2

import DetailFragment
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import database.AppDatabase
import database.ClosetItemViewModelFactory


class LandingPage : AppCompatActivity() {

    private lateinit var viewModel: ClosetItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_landing_page)

        val database = AppDatabase.getDatabase(this)
        val itemDao = database.itemDao()
        val factory = ClosetItemViewModelFactory(itemDao)
        viewModel = ViewModelProvider(this, factory)[ClosetItemViewModel::class.java]

        //only goes off once
        viewModel.populateInitialData()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val listFragment = ListFragment()
        val detailFragment = DetailFragment()
        //val editFragment = EditFragment()
        val addFragment = AddFragment()


        //sets initial view on activity_landing_page to be the list of clothes
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragment, listFragment)
            commit()
        }


        val btnAdd = findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.layoutFragment, addFragment)
                commit()
            }
        }
    }
}

