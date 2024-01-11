package com.oop.lab5.my_editor

interface PaintMessagesHandler {
    var isRubberTraceModeOn: Boolean

    fun onFingerTouch(x: Float, y: Float)
    fun onFingerMove(x: Float, y: Float)
    fun onFingerRelease()
    fun onPaint()
}