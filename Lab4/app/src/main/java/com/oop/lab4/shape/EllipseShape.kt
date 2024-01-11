package com.oop.lab4.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.oop.lab4.R

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

    override fun getFillingPaint(): Paint {
        return super.getFillingPaint().apply {
            color = context.getColor(R.color.light_green)
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
        show(canvas, getOutlinePaint(), getFillingPaint())
    }
}
