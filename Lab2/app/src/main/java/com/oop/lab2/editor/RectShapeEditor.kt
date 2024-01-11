package com.oop.lab2.editor

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

class RectShapeEditor: ShapeEditor() {
    private val shapeCenterPoint = PointF()

    override fun onFingerTouch(x: Float, y: Float) {
        shapeCenterPoint.set(x, y)
    }

    override fun onFingerMove(canvas: Canvas, paint: Paint, x: Float, y: Float) {
        val shapeStartX: Float
        val shapeStartY: Float
        val shapeEndX: Float
        val shapeEndY: Float

        val dx = x - shapeCenterPoint.x
        val oppositeX = shapeCenterPoint.x - dx
        if (x < shapeCenterPoint.x) {
            shapeStartX = x
            shapeEndX = oppositeX
        } else {
            shapeStartX = oppositeX
            shapeEndX = x
        }

        val dy = y - shapeCenterPoint.y
        val oppositeY = shapeCenterPoint.y - dy
        if (y < shapeCenterPoint.y) {
            shapeStartY = y
            shapeEndY = oppositeY
        } else {
            shapeStartY = oppositeY
            shapeEndY = y
        }

        shape.setStart(shapeStartX, shapeStartY)
        shape.setEnd(shapeEndX, shapeEndY)
        setRubberTraceMode()
        shape.show(canvas, paint)
    }
}
