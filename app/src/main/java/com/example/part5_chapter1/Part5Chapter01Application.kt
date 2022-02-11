package com.example.part5_chapter1

import android.app.Application
import com.example.part5_chapter1.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Part5Chapter01Application: Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Part5Chapter01Application)
            modules(appModule)


        }

    }

}
