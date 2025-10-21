package com.icc.practica8

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

class PrimerWidget: AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        //se ejecuta al agregar el widget o hacer actualizacion
        for(id in appWidgetIds){
            val views = RemoteViews(context.packageName,
                R.layout.primerwidget)
            appWidgetManager.updateAppWidget(id,views)
        }
    }
}