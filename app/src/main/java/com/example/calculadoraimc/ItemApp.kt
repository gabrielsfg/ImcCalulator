package com.example.calculadoraimc

import android.app.Application

class ItemApp: Application() {

    val db by lazy {
        ImcDataBase.getInstance(this)
    }

}