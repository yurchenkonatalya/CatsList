package com.example.catslist

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.catslist.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.Nullable


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.let{
            NavigationUI.setupWithNavController(it, navController)
        }
//        val spinner = binding.spinner
//        val aboutDeveloper = binding.developer
//        spinner.onItemSelectedListener = aboutDeveloper
//        val elements: MutableList<String> = ArrayList()
//        elements.add("Элемент 1")
//        elements.add("Элемент 2")
//        elements.add("Элемент 3")
//        elements.add("Элемент 4")
//        elements.add("Элемент 5")
//        elements.add("Элемент 6")
//        val dataAdapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_spinner_item, elements
//        )
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.setAdapter(dataAdapter)




    }
}