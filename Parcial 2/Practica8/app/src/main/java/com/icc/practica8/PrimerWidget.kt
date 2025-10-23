package com.icc.practica8

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class PrimerWidget: AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        manager: AppWidgetManager,
        ids: IntArray
    ) {
        for(id in ids) actualizarWidget(context,id)
    }

    companion object{
        fun actualizarWidget(context: Context, appWidgetId: Int){
            val prefs = context.getSharedPreferences("WIDGET_PREFS", Context.MODE_PRIVATE)
            val msg = prefs.getString("msg_$appWidgetId",
                "Hola desde primerWidget")?: "Hola desde widget"

            val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

            val views = RemoteViews(context.packageName, R.layout.primerwidget2)

            views.setTextViewText(R.id.txtMensaje,msg)
            views.setTextViewText(R.id.txtHora,hora)

            val intent = Intent(context, MainActivity::class.java)
            val pi = PendingIntent.getActivity(
                context,appWidgetId,intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            views.setOnClickPendingIntent(R.id.panelMarco,pi)

            val color = if(Random.nextBoolean()) android.R.color.darker_gray
                        else android.R.color.holo_blue_light

            views.setInt(R.id.panelMarco,"setBackgroundResource",
                color)

            AppWidgetManager.getInstance(context).updateAppWidget(
                appWidgetId,views
            )
        }
    }
}