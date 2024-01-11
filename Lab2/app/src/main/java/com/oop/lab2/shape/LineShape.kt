package com.oop.lab2.shape

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class LineShape: Shape() {
    override val defaultOutlineColor = Color.argb(255, 0, 0, 0)
    override val defaultFillingColor = Color.TRANSPARENT

    override fun show(canvas: Canvas, paint: Paint) {
        if (outlineColor == null) {
            paint.color = defaultOutlineColor
        } else {
            paint.color = outlineColor as Int
            outlineColor = null
        }
        paint.style = Paint.Style.STROKE
        canvas.drawLine(startX, startY, endX, endY, paint)
    }
}
