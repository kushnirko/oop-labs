package com.oop.lab2

import android.graphics.Canvas
import android.graphics.Paint

import com.oop.lab2.shape_editor.ShapeObjectsEditorInterface

interface PaintViewInterface {
    val paint: Paint

    var shapeObjectsEditor: ShapeObjectsEditorInterface

    val drawnShapesCanvas: Canvas

    val rubberTraceCanvas: Canvas

    fun repaint()

    fun clearCanvas(canvas: Canvas)
}
