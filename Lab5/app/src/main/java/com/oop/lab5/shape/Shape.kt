package com.oop.lab5.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
import com.oop.lab5.R

abstract class Shape(private val context: Context) {
    abstract val name: String
    val associatedIds = mutableMapOf<String, Int>()

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

    fun getCoords(): RectF {
        return RectF(startX, startY, endX, endY)
    }

    protected open fun getOutlinePaint(mode: String): Paint {
        return Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 7F
            val modeActions = mapOf(
                "default" to {
                    color = context.getColor(R.color.black)
                },
                "selected" to {
                    color = context.getColor(R.color.selected_outline_color)
                },
                "rubberTrace" to {
                    color = context.getColor(R.color.dark_blue)
                    val dashLen = 30F
                    val spaceLen = 15F
                    val dashDensity = floatArrayOf(dashLen, spaceLen, dashLen, spaceLen)
                    pathEffect = DashPathEffect(dashDensity, 0F)
                },
            )
            modeActions[mode]?.invoke()
        }
    }

    protected open fun getFillingPaint(mode: String): Paint {
        return Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    abstract fun show(canvas: Canvas, outlinePaint: Paint, fillingPaint: Paint?)

    abstract fun showDefault(canvas: Canvas)

    abstract fun showSelected(canvas: Canvas)

    fun showRubberTrace(canvas: Canvas) {
        show(canvas, getOutlinePaint("rubberTrace"), null)
    }
}
