package com.gabi.marvel_explorer

import android.app.Application

class MarvelExplorerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MarvelExplorerApp
    }
}