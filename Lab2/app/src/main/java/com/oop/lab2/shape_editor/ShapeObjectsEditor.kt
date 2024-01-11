package com.oop.lab2.shape_editor

import com.oop.lab2.PaintViewInterface

// Імпорт пакетів модуля editor
import com.oop.lab2.editor.ShapeEditor
import com.oop.lab2.editor.PointShapeEditor
import com.oop.lab2.editor.LineShapeEditor
import com.oop.lab2.editor.RectShapeEditor
import com.oop.lab2.editor.EllipseShapeEditor

// Імпорт пакетів модуля shape
import com.oop.lab2.shape.Shape
import com.oop.lab2.shape.PointShape
import com.oop.lab2.shape.LineShape
import com.oop.lab2.shape.RectShape
import com.oop.lab2.shape.EllipseShape

class ShapeObjectsEditor: ShapeObjectsEditorInterface {
    override var isRubberTraceModeOn = false
    override lateinit var paintView: PaintViewInterface

    private val drawnShapes = mutableListOf<Shape>()
    private lateinit var activeEditor: ShapeEditor
    private lateinit var currentShape: () -> Shape

    override fun startPointEditor() {
        activeEditor = PointShapeEditor()
        currentShape = { PointShape() }
    }

    override fun startLineEditor() {
        activeEditor = LineShapeEditor()
        currentShape = { LineShape() }
    }

    override fun startRectEditor() {
        activeEditor = RectShapeEditor()
        currentShape = { RectShape() }
    }

    override fun startEllipseEditor() {
        activeEditor = EllipseShapeEditor()
        currentShape = { EllipseShape() }
    }

    override fun onFingerTouch(x: Float, y: Float) {
        activeEditor.shape = currentShape()
        activeEditor.onFingerTouch(x, y)
        isRubberTraceModeOn = true
    }

    override fun onFingerMove(x: Float, y:Float) {
        paintView.clearCanvas(paintView.rubberTraceCanvas)
        activeEditor.onFingerMove(paintView.rubberTraceCanvas, paintView.paint, x, y)
        paintView.repaint()
    }

    override fun onFingerRelease() {
        activeEditor.onFingerRelease(drawnShapes)
        isRubberTraceModeOn = false
        paintView.repaint()
    }

    override fun onPaint() {
        paintView.clearCanvas(paintView.rubberTraceCanvas)
        paintView.clearCanvas(paintView.drawnShapesCanvas)
        drawnShapes.forEach { it.show(paintView.drawnShapesCanvas, paintView.paint) }
    }
}
