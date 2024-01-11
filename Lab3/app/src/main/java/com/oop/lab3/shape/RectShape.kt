package com.oop.lab3.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.oop.lab3.R

import com.oop.lab3.editor.ShapeEditor

class RectShape(private val context: Context, override val editor: ShapeEditor):
    Shape(context) {
    init {
        editor.shape = this
    }
    override val name = context.getString(R.string.rectangle)

    override fun isValid(): Boolean {
        return (startX != endX || startY != endY)
    }

    override fun getInstance(): Shape {
        return RectShape(context, editor).also {
            it.associatedIds.putAll(this.associatedIds)
        }
    }

    override fun show(canvas: Canvas, outlinePaint: Paint, fillingPaint: Paint?) {
        val rect = RectF(startX, startY, endX, endY)
        fillingPaint?.let {
            canvas.drawRect(rect, it)
        }
        canvas.drawRect(rect, outlinePaint)
    }

    override fun showDefault(canvas: Canvas) {
        show(canvas, getOutlinePaint(), null)
    }
}
