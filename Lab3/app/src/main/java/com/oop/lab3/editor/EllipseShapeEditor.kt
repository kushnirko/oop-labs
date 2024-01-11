package com.oop.lab3.editor

import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.RectF

class EllipseShapeEditor : ShapeEditor() {
    private val shapeCenterPoint = PointF()

    override fun onFingerTouch(x: Float, y: Float) {
        shapeCenterPoint.set(x, y)
        shape.setStart(x, y)
        shape.setEnd(x, y)
    }

    override fun onFingerMove(canvas: Canvas, x: Float, y: Float) {
        val dx = x -  shapeCenterPoint.x
        val oppositeX = shapeCenterPoint.x - dx
        val dy = y - shapeCenterPoint.y
        val oppositeY = shapeCenterPoint.y - dy
        val enclosingRect = RectF(oppositeX, oppositeY - dy, x, y).apply { sort() }
        shape.setStart(enclosingRect.left, enclosingRect.top)
        shape.setEnd(enclosingRect.right, enclosingRect.bottom)
        shape.showRubberTrace(canvas)
    }
}
