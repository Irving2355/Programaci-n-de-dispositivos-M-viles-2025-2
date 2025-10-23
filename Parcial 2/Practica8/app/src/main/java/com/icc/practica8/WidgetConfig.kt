package com.icc.practica8

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class WidgetConfig : Activity() {
    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.widgetconfig)

        setResult(RESULT_CANCELED) //valor por defecto

        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )?: AppWidgetManager.INVALID_APPWIDGET_ID

        if(appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish(); return
        }

        val edt = findViewById<EditText>(R.id.edtMensaje)
        findViewById<Button>(R.id.btnCancelar).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnAceptar).setOnClickListener {
            val msg = edt.text.toString().ifBlank { "Hola desde WidgetConfig" }
            val prefs = getSharedPreferences("WIDGET_PREFS", Context.MODE_PRIVATE)
            prefs.edit().putString("msg_$appWidgetId",msg).apply()

            //actualizar
            PrimerWidget.actualizarWidget(this,appWidgetId)

            val result = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId)
            setResult(RESULT_OK,result)
            finish()
        }
    }
}