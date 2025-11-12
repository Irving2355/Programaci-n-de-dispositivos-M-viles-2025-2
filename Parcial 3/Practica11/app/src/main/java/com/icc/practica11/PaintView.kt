package com.icc.practica11

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.hypot

class PaintView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context,attrs){

    enum class Tool{PEN, POINT, LINE, RECT, CIRCLE, OVAL, ARC, TEXT}

    data class Stroke(
        val tool: Tool,
        val paint: Paint,
        val start: PointF,
        var end: PointF = PointF(),
        var path: Path? = null
    )

    private val strokes = mutableListOf<Stroke>()
    private var current: Stroke? = null

    var currentColor = Color.BLACK
    var strokeWidthPx = 8f
    var currentTool = Tool.PEN

    private fun newPaint() = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        style = Paint.Style.STROKE
        color = currentColor
        strokeWidth = strokeWidthPx
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        strokes.forEach { drawStroke(canvas, it) }
        current?.let { drawStroke(canvas, it)}
    }

    private fun drawStroke(canvas: Canvas, s: Stroke){
        when (s.tool){
            Tool.PEN -> s.path?.let {canvas.drawPath(it,s.paint)}

            Tool.RECT -> canvas.drawRect(RectF(s.start.x,s.start.y,
                s.end.x, s.end.y),s.paint)

            Tool.POINT -> canvas.drawPoint(s.start.x, s.start.y,s.paint)

            Tool.LINE -> canvas.drawLine(s.start.x,s.start.y,
                s.end.x,s.end.y,s.paint)

            Tool.CIRCLE -> {
                val r = hypot((s.end.x - s.start.x).toDouble(),
                    (s.end.y - s.start.y).toDouble()).toFloat()
                canvas.drawCircle(s.start.x,s.start.y,r,s.paint)
            }

            Tool.OVAL -> canvas.drawOval(RectF(s.start.x,s.start.y,
                s.end.x,s.end.y),s.paint)

            Tool.ARC -> canvas.drawArc(RectF(s.start.x,s.start.y,
                s.end.x,s.end.y),
                0f, 120f, false,s.paint)

            Tool.TEXT -> canvas.drawText("Hola canvas",s.start.x,
                s.start.y,s.paint)
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val x = e.x; val y = e.y
        when(e.action){
            MotionEvent.ACTION_DOWN -> {
                val p = newPaint()
                current = when (currentTool){
                    Tool.PEN -> Stroke(Tool.PEN,p,PointF(x,y),
                        path = Path().apply { moveTo(x,y) })
                    else -> Stroke(currentTool,p,PointF(x,y))
                }
            }

            MotionEvent.ACTION_MOVE -> current?.apply {
                if(tool == Tool.PEN) path?.lineTo(x,y) else end.set(x,y)
            }

            MotionEvent.ACTION_UP -> current?.let { s->
                if(s.tool != Tool.PEN) s.end.set(x,y)
                strokes.add(s)
                current = null
            }
        }
        invalidate()
        return true
    }

    fun undo() {if(strokes.isNotEmpty()){
        //strokes.removeLast() requiere API 35
        strokes.removeAt(strokes.lastIndex)
        invalidate()}}

    fun clearAll(){strokes.clear();invalidate()}
}