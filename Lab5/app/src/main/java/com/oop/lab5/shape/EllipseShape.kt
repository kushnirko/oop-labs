package com.oop.lab5.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.oop.lab5.R

class EllipseShape(private val context: Context): Shape(context) {
    override val name = context.getString(R.string.ellipse)

    override fun isValid(): Boolean {
        return (startX != endX || startY != endY)
    }

    override fun getInstance(): Shape {
        return EllipseShape(context).also {
            it.associatedIds.putAll(this.associatedIds)
        }
    }

    override fun getFillingPaint(mode: String): Paint {
        return super.getFillingPaint(mode).apply {
            val modeActions = mapOf(
                "default" to {
                    color = context.getColor(R.color.light_green)
                },
                "selected" to {
                    color = context.getColor(R.color.selected_filling_color)
                },
            )
            modeActions[mode]?.invoke()
        }
    }

    override fun show(canvas: Canvas, outlinePaint: Paint, fillingPaint: Paint?) {
        val dx = endX -  startX
        val dy = endY - startY
        val rect = RectF(startX - dx, startY - dy, endX, endY).apply { sort() }
        fillingPaint?.let {
            canvas.drawOval(rect, it)
        }
        canvas.drawOval(rect, outlinePaint)
    }

    override fun showDefault(canvas: Canvas) {
        show(canvas, getOutlinePaint("default"), getFillingPaint("default"))
    }

    override fun showSelected(canvas: Canvas) {
        show(canvas, getOutlinePaint("selected"), getFillingPaint("selected"))
    }
}
