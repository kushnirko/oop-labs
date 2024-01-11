package com.oop.lab4.my_editor

import android.content.Context

import com.oop.lab4.paint_view.PaintUtils
import com.oop.lab4.shape.Shape
import com.oop.lab4.shape.PointShape
import com.oop.lab4.shape.LineShape
import com.oop.lab4.shape.RectShape
import com.oop.lab4.shape.EllipseShape
import com.oop.lab4.shape.SegmentShape
import com.oop.lab4.shape.CuboidShape

class MyEditor(context: Context): PaintMessagesHandler {
    lateinit var paintUtils: PaintUtils
    override var isRubberTraceModeOn = false

    val shapes = arrayOf(
        PointShape(context),
        LineShape(context),
        RectShape(context),
        EllipseShape(context),
        SegmentShape(context),
        CuboidShape(context),
    )
    var currentShape: Shape? = null
        private set
    private val drawnShapes = mutableListOf<Shape>()

    fun start(shape: Shape) {
        currentShape = shape
    }

    fun close() {
        currentShape = null
    }

    override fun onFingerTouch(x: Float, y: Float) {
        currentShape?.apply {
            setStart(x, y)
            setEnd(x, y)
        }
    }

    override fun onFingerMove(x: Float, y: Float) {
        currentShape?.let {
            isRubberTraceModeOn = true
            paintUtils.clearCanvas(paintUtils.rubberTraceCanvas)
            it.setEnd(x, y)
            it.showRubberTrace(paintUtils.rubberTraceCanvas)
            paintUtils.repaint()
        }
    }

    override fun onFingerRelease() {
        currentShape = currentShape?.let {
            isRubberTraceModeOn = false
            if (it.isValid()) {
                drawnShapes.add(it)
            }
            paintUtils.repaint()
            it.getInstance()
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
