package com.icc.practica10

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import androidx.compose.foundation.Canvas

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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        extraBitmap = Bitmap.createBitmap(w,h,
            Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap,0f,0f,null)
        canvas.drawPath(path,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action){
            MotionEvent.ACTION_DOWN -> touchStart(x,y)
            MotionEvent.ACTION_MOVE -> touchMove(x,y)
            MotionEvent.ACTION_UP   -> touchUp()
        }
        invalidate()
        return true
    }

    private fun touchStart(x: Float, y : Float){
        //path.reset()
        path.moveTo(x,y)
        motionX = x
        motionY = y
    }

    private fun touchMove(x: Float, y : Float){
        val dx = Math.abs(x-motionX)
        val dy = Math.abs(y-motionY)
        if(dx >= touchTolerance || dy >= touchTolerance){
            path.quadTo(motionX,
                motionY,
                (x+motionX)/2,
                (y+motionY)/2)
            motionX = x
            motionY = y
        }
    }

    private fun touchUp(){
        path.lineTo(motionX,motionY)
        extraCanvas.drawPath(path,paint)
        //path.reset()
    }
}