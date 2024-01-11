package com.oop.lab2.editor

import android.graphics.Color

import com.oop.lab2.shape.Shape

abstract class ShapeEditor: Editor() {
    lateinit var shape: Shape

    override fun onFingerRelease(drawnShapes: MutableList<Shape>) {
        drawnShapes.add(shape)
    }

    protected fun setRubberTraceMode() {
        val outlineColor = Color.argb(255, 255, 0, 0)
        val fillingColor = Color.TRANSPARENT
        shape.setColors(outlineColor, fillingColor)
    }
}
