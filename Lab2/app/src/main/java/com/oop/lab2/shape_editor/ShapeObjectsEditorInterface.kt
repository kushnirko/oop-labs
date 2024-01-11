package com.oop.lab2.shape_editor

import com.oop.lab2.PaintViewInterface

interface ShapeObjectsEditorInterface {
    var isRubberTraceModeOn: Boolean
    var paintView: PaintViewInterface

    fun startPointEditor()

    fun startLineEditor()

    fun startRectEditor()

    fun startEllipseEditor()

    fun onFingerTouch(x: Float, y: Float)

    fun onFingerMove(x: Float, y:Float)

    fun onFingerRelease()

    fun onPaint()
}
