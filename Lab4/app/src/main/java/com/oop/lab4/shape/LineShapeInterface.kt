package com.oop.lab4.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

interface LineShapeInterface {
    fun lineShapeShow(context: Context, canvas: Canvas, paint: Paint,
                      startPoint: PointF, endPoint: PointF) {
        val lineShape = LineShape(context)
        lineShape.setStart(startPoint.x, startPoint.y)
        lineShape.setEnd(endPoint.x, endPoint.y)
        lineShape.show(canvas, paint, null)
    }
}