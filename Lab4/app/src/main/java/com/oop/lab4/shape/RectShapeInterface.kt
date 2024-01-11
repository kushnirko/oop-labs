package com.oop.lab4.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

interface RectShapeInterface {
    fun rectShapeShow(context: Context, canvas: Canvas,
                      outlinePaint: Paint, fillingPaint: Paint?,
                      rect: RectF) {
        val rectShape = RectShape(context)
        rectShape.setStart(rect.left, rect.top)
        rectShape.setEnd(rect.right, rect.bottom)
        rectShape.show(canvas, outlinePaint, fillingPaint)
    }
}