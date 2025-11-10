package com.icc.practica11

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

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
}