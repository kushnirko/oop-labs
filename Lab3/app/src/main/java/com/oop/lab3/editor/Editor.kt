package com.oop.lab3.editor

import android.graphics.Canvas

import com.oop.lab3.shape.Shape

abstract class Editor {
    abstract fun onFingerTouch(x: Float, y: Float)

    abstract fun onFingerMove(canvas: Canvas, x: Float, y: Float)

    abstract fun onFingerRelease(drawnShapes: MutableList<Shape>)
}
