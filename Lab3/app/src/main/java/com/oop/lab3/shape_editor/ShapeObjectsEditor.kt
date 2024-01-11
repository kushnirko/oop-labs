package com.oop.lab3.shape_editor

import android.content.Context

import com.oop.lab3.shape.Shape
import com.oop.lab3.shape.PointShape
import com.oop.lab3.shape.LineShape
import com.oop.lab3.shape.RectShape
import com.oop.lab3.shape.EllipseShape

import com.oop.lab3.editor.ShapeEditor
import com.oop.lab3.editor.PointShapeEditor
import com.oop.lab3.editor.LineShapeEditor
import com.oop.lab3.editor.RectShapeEditor
import com.oop.lab3.editor.EllipseShapeEditor

import com.oop.lab3.paint_view.PaintUtils

class ShapeObjectsEditor(context: Context): PaintMessagesHandler {
    lateinit var paintUtils: PaintUtils
    override var isRubberTraceModeOn = false

    val shapes = arrayOf(
        PointShape(context, PointShapeEditor()),
        LineShape(context, LineShapeEditor()),
        RectShape(context, RectShapeEditor()),
        EllipseShape(context, EllipseShapeEditor()),
    )
    var currentShape: Shape? = null
        private set
    private val drawnShapes = mutableListOf<Shape>()
    private var activeEditor: ShapeEditor? = null

    fun startEditor(shape: Shape) {
        currentShape = shape
        activeEditor = shape.editor
    }

    fun closeEditor() {
        currentShape = null
        activeEditor = null
    }

    override fun onFingerTouch(x: Float, y: Float) {
        activeEditor?.onFingerTouch(x, y)
    }

    override fun onFingerMove(x: Float, y:Float) {
        activeEditor?.let {
            isRubberTraceModeOn = true
            paintUtils.clearCanvas(paintUtils.rubberTraceCanvas)
            it.onFingerMove(paintUtils.rubberTraceCanvas, x, y)
            paintUtils.repaint()
        }
    }

    override fun onFingerRelease() {
        activeEditor?.let {
            isRubberTraceModeOn = false
            it.onFingerRelease(drawnShapes)
            paintUtils.repaint()
        }
    }

    override fun onPaint() {
        paintUtils.clearCanvas(paintUtils.rubberTraceCanvas)
        paintUtils.clearCanvas(paintUtils.drawnShapesCanvas)
        drawnShapes.forEach {
            it.showDefault(paintUtils.drawnShapesCanvas)
        }
    }

    fun undo() {
        if (drawnShapes.size > 0) {
            drawnShapes.removeLast()
            paintUtils.repaint()
        }
    }

    fun clearAll() {
        if (drawnShapes.size > 0) {
            drawnShapes.clear()
            paintUtils.repaint()
        }
    }
}
