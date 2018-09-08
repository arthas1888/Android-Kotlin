package co.edu.aulamatriz.netapplication

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger.addLogAdapter
import com.orhanobut.logger.PrettyFormatStrategy
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger


class App : Application() {

    companion object {
        lateinit var instance: App
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Net App Log")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}