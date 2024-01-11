package com.oop.lab2.editor

import android.graphics.Canvas
import android.graphics.Paint

import com.oop.lab2.shape.Shape

abstract class Editor {
    abstract fun onFingerTouch(x: Float, y: Float)

    abstract fun onFingerMove(canvas: Canvas, paint: Paint, x: Float, y: Float)

    abstract fun onFingerRelease(drawnShapes: MutableList<Shape>)
}
