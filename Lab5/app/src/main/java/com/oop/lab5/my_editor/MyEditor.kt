package com.oop.lab5.my_editor

import android.content.Context
import java.lang.StringBuilder

import com.oop.lab5.paint_view.PaintUtils
import com.oop.lab5.shape.Shape
import com.oop.lab5.shape.PointShape
import com.oop.lab5.shape.LineShape
import com.oop.lab5.shape.RectShape
import com.oop.lab5.shape.EllipseShape
import com.oop.lab5.shape.SegmentShape
import com.oop.lab5.shape.CuboidShape
import com.oop.lab5.tooltip.Tooltip

class MyEditor private constructor(): PaintMessagesHandler {
    companion object {
        @Volatile
        private lateinit var instance: MyEditor

        fun getInstance(): MyEditor {
            synchronized(this) {
                if (!::instance.isInitialized) instance = MyEditor()
                return instance
            }
        }
    }

    lateinit var paintUtils: PaintUtils
    override var isRubberTraceModeOn = false

    lateinit var shapes: Array<Shape>
    var currentShape: Shape? = null
        private set
    private val drawnShapes = mutableListOf<Shape>()
    private val selectedShapesIndices = mutableListOf<Int>()

    private var onNewShapeListener: ((Shape) -> Unit)? = null

    private lateinit var onUndoListener: () -> Unit
    private lateinit var onClearAllListener: () -> Unit

    private lateinit var emptyDrawingTooltip: Tooltip

    fun onCreate(context: Context) {
        shapes = arrayOf(
            PointShape(context),
            LineShape(context),
            RectShape(context),
            EllipseShape(context),
            SegmentShape(context),
            CuboidShape(context),
        )
        emptyDrawingTooltip = Tooltip(context)
    }

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
            if (it.isValid()) addShape(it)
            paintUtils.repaint()
            it.getInstance()
        }
    }

    override fun onPaint() {
        paintUtils.clearCanvas(paintUtils.rubberTraceCanvas)
        paintUtils.clearCanvas(paintUtils.drawnShapesCanvas)
        drawnShapes.forEach {
            if (selectedShapesIndices.contains(drawnShapes.indexOf(it))) {
                it.showSelected(paintUtils.drawnShapesCanvas)
            } else {
                it.showDefault(paintUtils.drawnShapesCanvas)
            }
        }
    }

    fun serializeShape(shape: Shape): String {
        val str = StringBuilder()
        val coords = shape.getCoords()
        val fields = arrayOf(
            shape.name,
            coords.left.toInt(),
            coords.top.toInt(),
            coords.right.toInt(),
            coords.bottom.toInt()
        )
        (0..<(fields.size - 1)).forEach {
            str.append("${fields[it]}\t")
        }
        str.append("${fields.last()}")
        return str.toString()
    }

    fun deserializeShape(serializedShape: String): Shape {
        val data = serializedShape.split("\t")
        val fields = object {
            val name = data[0]
            val startX = data[1].toFloat()
            val startY = data[2].toFloat()
            val endX = data[3].toFloat()
            val endY = data[4].toFloat()
        }
        val shape = shapes.find { fields.name == it.name }!!.getInstance()
        shape.setStart(fields.startX, fields.startY)
        shape.setEnd(fields.endX, fields.endY)
        return shape
    }

    fun serializeDrawing(): String {
        val str = StringBuilder()
        drawnShapes.forEach {
            str.append("${serializeShape(it)}\n")
        }
        return str.toString()
    }

    fun deserializeDrawing(serializedDrawing: String) {
        if (!isDrawingEmpty()) clearAll()
        val serializedShapes = serializedDrawing.dropLast(1).split("\n")
        serializedShapes.forEach {
            addShape(deserializeShape(it))
        }
        paintUtils.repaint()
    }

    fun addShape(shape: Shape) {
        drawnShapes.add(shape)
        onNewShapeListener?.invoke(shape)
    }

    fun selectShape(index: Int) {
        selectedShapesIndices.add(index)
        paintUtils.repaint()
    }

    fun cancelShapes(indices: List<Int>) {
        for (index in indices) {
            selectedShapesIndices.remove(index)
        }
        paintUtils.repaint()
    }

    fun deleteShapes(indices: List<Int>) {
        if (!isDrawingEmpty()) {
            for (index in indices.sorted().sortedDescending()) {
                selectedShapesIndices.remove(index)
                drawnShapes.removeAt(index)
            }
            paintUtils.repaint()
        } else {
            emptyDrawingTooltip.create("Полотно уже порожнє").display()
        }
    }

    fun isDrawingEmpty(): Boolean {
        return drawnShapes.isEmpty()
    }

    fun undo() {
        deleteShapes(listOf(drawnShapes.size - 1))
        onUndoListener()
    }

    fun clearAll() {
        deleteShapes((0..<drawnShapes.size).toList())
        onClearAllListener()
    }

    fun setOnNewShapeListener(listener: ((Shape) -> Unit)?) {
        onNewShapeListener = listener
    }

    fun setOnUndoListener(listener: () -> Unit) {
        onUndoListener = listener
    }

    fun setOnClearAllListener(listener: () -> Unit) {
        onClearAllListener = listener
    }
}
