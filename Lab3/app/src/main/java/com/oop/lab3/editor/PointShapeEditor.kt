package com.oop.lab3.editor

import android.graphics.Canvas

class PointShapeEditor: ShapeEditor() {
    override fun onFingerTouch(x: Float, y: Float) {
        shape.setStart(x, y)
    }

    override fun onFingerMove(canvas: Canvas, x: Float, y: Float) {
        shape.showRubberTrace(canvas)
    }
}
