package com.oop.lab3.editor

import android.graphics.Canvas

class RectShapeEditor: ShapeEditor() {
    override fun onFingerTouch(x: Float, y: Float) {
        shape.setStart(x, y)
        shape.setEnd(x, y)
    }

    override fun onFingerMove(canvas: Canvas, x: Float, y: Float) {
        shape.setEnd(x, y)
        shape.showRubberTrace(canvas)
    }
}
