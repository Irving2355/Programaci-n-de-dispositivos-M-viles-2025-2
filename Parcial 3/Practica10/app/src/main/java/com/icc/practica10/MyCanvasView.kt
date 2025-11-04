package com.icc.practica10

import android.content.Context
import android.graphics.*
import android.view.View

class MyCanvasView(context: Context) : View(context) {
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private val drawColor = Color.WHITE
    private val backgroundColor = Color.WHITE
    private val paint = Paint().apply {
        color = Color.BLUE
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }

    private var path = Path()
    private var motionX = 0f
    private var motionY = 0f
    private val touchTolerance = 4f
}