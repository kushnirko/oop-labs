package com.oop.lab2.shape

import android.graphics.Canvas
import android.graphics.Paint

abstract class Shape {
    protected var startX = 0F
    protected var startY = 0F
    protected var endX = 0F
    protected var endY = 0F

    protected abstract val defaultOutlineColor: Int
    protected abstract val defaultFillingColor: Int
    protected var outlineColor: Int? = null
    protected var fillingColor: Int? = null

    fun setStart(x: Float, y: Float) {
        startX = x
        startY = y
    }

    fun setEnd(x: Float, y: Float) {
        endX = x
        endY = y
    }

    fun setColors(outlineColor: Int, fillingColor: Int) {
        this.outlineColor = outlineColor
        this.fillingColor = fillingColor
    }

    abstract fun show(canvas: Canvas, paint: Paint)
}
