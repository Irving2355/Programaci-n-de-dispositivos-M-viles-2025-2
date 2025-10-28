package com.icc.practica9

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class ContadorWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for(id in appWidgetIds){
            actualizarWidget(context,appWidgetManager,id)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        when(intent.action){
            ACTION_APP_EVENT -> {
                //evento interno de la app sucedio refresh
                // a todas las instancias
                val manager = AppWidgetManager.getInstance(context)
                val ids = manager.getAppWidgetIds(
                    ComponentName(context,
                        ContadorWidgetProvider::class.java)
                )
                for(id in ids) actualizarWidget(context,manager,id)
            }
        }
    }

    private fun actualizarWidget(context: Context, manager: AppWidgetManager,
                                 id: Int){
        val prefs = context.getSharedPreferences(PREFS,
            Context.MODE_PRIVATE)
        val valor = prefs.getInt(KEY_CONTADOR,0)
        val views = RemoteViews(context.packageName,
            R.layout.widget_contador)

        views.setTextViewText(R.id.txtValor,valor.toString())

        val intent = Intent(context,
            MainActivity::class.java)
        val pi = PendingIntent.getActivity(
            context,id,intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.btnAbrirApp,pi)
        manager.updateAppWidget(id,views)
    }

    companion object{
        const val ACTION_APP_EVENT = "com.icc.practica9.ACTION_APP_EVENT"
        const val PREFS = "WIDGET_REACTIVO_PREFS"
        const val KEY_CONTADOR = "contador_global"
    }
}