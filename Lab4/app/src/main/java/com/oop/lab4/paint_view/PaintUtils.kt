package com.oop.lab4.paint_view

import android.graphics.Canvas

interface PaintUtils {
    val drawnShapesCanvas: Canvas
    val rubberTraceCanvas: Canvas

    fun repaint()
    fun clearCanvas(canvas: Canvas)
}
