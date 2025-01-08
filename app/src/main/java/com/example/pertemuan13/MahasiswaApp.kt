package com.example.pertemuan13

import android.app.Application
import com.example.pertemuan13.di.AppContainer
import com.example.pertemuan13.di.MahasiswaContainer


class MahasiswaApplications: Application(){
    lateinit var container: MahasiswaContainer
    override fun onCreate() {
        super.onCreate()
        container= MahasiswaContainer()
    }
}
