package com.oop.lab2.editor

import android.graphics.Canvas
import android.graphics.Paint

class LineShapeEditor: ShapeEditor() {
    override fun onFingerTouch(x: Float, y: Float) {
        shape.setStart(x, y)
    }

    override fun onFingerMove(canvas: Canvas, paint: Paint, x: Float, y: Float) {
        shape.setEnd(x, y)
        setRubberTraceMode()
        shape.show(canvas, paint)
    }
}
