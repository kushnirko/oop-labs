package com.oop.lab5.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.oop.lab5.R

class LineShape(private val context: Context): Shape(context) {
    override val name = context.getString(R.string.line)

    override fun isValid(): Boolean {
        return (startX != endX || startY != endY)
    }

    override fun getInstance(): Shape {
        return LineShape(context).also {
            it.associatedIds.putAll(this.associatedIds)
        }
    }

    override fun show(canvas: Canvas, outlinePaint: Paint, fillingPaint: Paint?) {
        canvas.drawLine(startX, startY, endX, endY, outlinePaint)
    }

    override fun showDefault(canvas: Canvas) {
        show(canvas, getOutlinePaint("default"), null)
    }

    override fun showSelected(canvas: Canvas) {
        show(canvas, getOutlinePaint("selected"), null)
    }
}
