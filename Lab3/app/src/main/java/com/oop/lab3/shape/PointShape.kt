package com.oop.lab3.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.oop.lab3.R

import com.oop.lab3.editor.ShapeEditor

class PointShape(private val context: Context, override val editor: ShapeEditor):
    Shape(context) {
    init {
        editor.shape = this
    }
    override val name = context.getString(R.string.point)

    override fun isValid(): Boolean {
        return true
    }

    override fun getInstance(): Shape {
        return PointShape(context, editor).also {
            it.associatedIds.putAll(this.associatedIds)
        }
    }

    override fun getOutlinePaint(): Paint {
        return super.getOutlinePaint().apply {
            strokeWidth = 15F
        }
    }

    override fun getRubberTracePaint(): Paint {
        return super.getRubberTracePaint().apply {
            strokeWidth = 15F
        }
    }

    override fun show(canvas: Canvas, outlinePaint: Paint, fillingPaint: Paint?) {
        canvas.drawPoint(startX, startY, outlinePaint)
    }

    override fun showDefault(canvas: Canvas) {
        show(canvas, getOutlinePaint(), null)
    }
}
