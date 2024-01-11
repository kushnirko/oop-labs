package com.oop.lab2.shape

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class RectShape: Shape() {
    override val defaultOutlineColor = Color.argb(255, 0, 0, 0)
    override val defaultFillingColor = Color.TRANSPARENT

    override fun show(canvas: Canvas, paint: Paint) {
        val startX = startX.toInt()
        val startY = startY.toInt()
        val endX = endX.toInt()
        val endY = endY.toInt()
        val rect = Rect(startX, startY, endX, endY)
        // Малюємо контур прямокутника
        if (outlineColor == null) {
            paint.color = defaultOutlineColor
        } else {
            paint.color = outlineColor as Int
            outlineColor = null
        }
        paint.style = Paint.Style.STROKE
        canvas.drawRect(rect, paint)
        // Малюємо заповнення прямокутника
        if (fillingColor == null) {
            paint.color = defaultFillingColor
        } else {
            paint.color = fillingColor as Int
            fillingColor = null
        }
        paint.style = Paint.Style.FILL
        canvas.drawRect(rect, paint)
    }
}
