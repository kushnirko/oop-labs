package com.oop.lab2.shape

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class EllipseShape: Shape() {
    override val defaultOutlineColor = Color.argb(255, 0, 0, 0)
    override val defaultFillingColor = Color.argb(255, 255, 255, 0)

    override fun show(canvas: Canvas, paint: Paint) {
        val rect = RectF(startX, startY, endX, endY)
        // Малюємо контур еліпса
        if (outlineColor == null) {
            paint.color = defaultOutlineColor
        } else {
            paint.color = outlineColor as Int
            outlineColor = null
        }
        paint.style = Paint.Style.STROKE
        canvas.drawOval(rect, paint)
        // Малюємо заповнення еліпса
        if (fillingColor == null) {
            paint.color = defaultFillingColor
        } else {
            paint.color = fillingColor as Int
            fillingColor = null
        }
        paint.style = Paint.Style.FILL
        canvas.drawOval(rect, paint)
    }
}
