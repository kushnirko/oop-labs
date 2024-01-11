package com.oop.lab5.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.oop.lab5.R

class PointShape(private val context: Context): Shape(context) {
    override val name = context.getString(R.string.point)

    override fun isValid(): Boolean {
        return true
    }

    override fun getInstance(): Shape {
        return PointShape(context).also {
            it.associatedIds.putAll(this.associatedIds)
        }
    }

    override fun getOutlinePaint(mode: String): Paint {
        return super.getOutlinePaint(mode).apply {
            strokeWidth = 15F
        }
    }

    override fun show(canvas: Canvas, outlinePaint: Paint, fillingPaint: Paint?) {
        canvas.drawPoint(startX, startY, outlinePaint)
    }

    override fun showDefault(canvas: Canvas) {
        show(canvas, getOutlinePaint("default"), null)
    }

    override fun showSelected(canvas: Canvas) {
        show(canvas, getOutlinePaint("selected"), null)
    }
}
