package com.oop.lab3.editor

import com.oop.lab3.shape.Shape

abstract class ShapeEditor: Editor() {
    lateinit var shape: Shape

    override fun onFingerRelease(drawnShapes: MutableList<Shape>) {
        if (shape.isValid()) {
            drawnShapes.add(shape)
        }
        shape = shape.getInstance()
    }
}
