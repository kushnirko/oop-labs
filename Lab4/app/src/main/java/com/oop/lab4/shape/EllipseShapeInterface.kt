package com.oop.lab4.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

interface EllipseShapeInterface {
    fun ellipseShapeShow(context: Context, canvas: Canvas,
                         outlinePaint: Paint, fillingPaint: Paint?,
                         centerPoint: PointF, radius: Float) {
        val ellipseShape = EllipseShape(context)
        ellipseShape.setStart(centerPoint.x, centerPoint.y)
        ellipseShape.setEnd(centerPoint.x + radius, centerPoint.y + radius)
        ellipseShape.show(canvas, outlinePaint, fillingPaint)
    }
}
