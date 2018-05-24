package org.firespeed.phpconfukuoka18

import android.app.Application
import org.firespeed.phpconfukuoka18.model.OrmaHolder

class BasicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        OrmaHolder.initialize(this)
    }

}