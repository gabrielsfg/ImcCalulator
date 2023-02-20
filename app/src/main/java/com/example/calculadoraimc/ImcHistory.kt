package com.example.calculadoraimc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class ImcHistory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_history)

        var toolbarHistory: Toolbar = findViewById(R.id.toolbar_history_imc)
        setSupportActionBar(toolbarHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarHistory.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}