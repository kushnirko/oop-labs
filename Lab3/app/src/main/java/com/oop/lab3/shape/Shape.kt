package com.oop.lab3.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.oop.lab3.R

import com.oop.lab3.editor.ShapeEditor

abstract class Shape(private val context: Context) {
    abstract val name: String
    val associatedIds = mutableMapOf<String, Int>()
    abstract val editor: ShapeEditor

    protected var startX: Float = 0F
    protected var startY: Float = 0F
    protected var endX: Float = 0F
    protected var endY: Float = 0F

    fun setStart(x: Float, y: Float) {
        startX = x
        startY = y
    }

    fun setEnd(x: Float, y: Float) {
        endX = x
        endY = y
    }

    abstract fun isValid(): Boolean

    abstract fun getInstance(): Shape

    protected open fun getOutlinePaint(): Paint {
        return Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 7F
            color = context.getColor(R.color.black)
        }
    }

    protected open fun getFillingPaint(): Paint {
        return Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    protected open fun getRubberTracePaint(): Paint {
        val paint = getOutlinePaint()
        paint.color = context.getColor(R.color.dark_blue)
        return paint
    }

    abstract fun show(canvas: Canvas, outlinePaint: Paint, fillingPaint: Paint?)

    abstract fun showDefault(canvas: Canvas)

    fun showRubberTrace(canvas: Canvas) {
        show(canvas, getRubberTracePaint(), null)
    }
}
