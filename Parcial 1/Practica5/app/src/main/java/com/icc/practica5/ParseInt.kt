package com.icc.practica5

import android.app.Application
import com.parse.Parse

class ParseInt: Application(){
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("LdxLhC41AAlTLfL0hVGv6dA52fNvKGO3nLthfpD2")
                .clientKey("P5t7uGojo6r1s5QoQL4dObF8rH000hY9mwNBKrlx")
                .server("https://parseapi.back4app.com/")
                .build()
        )
    }
}