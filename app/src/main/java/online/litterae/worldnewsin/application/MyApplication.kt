package online.litterae.worldnewsin.application

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: MyApplication
    }
}