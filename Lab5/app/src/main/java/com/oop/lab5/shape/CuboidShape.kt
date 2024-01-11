package com.oop.lab5.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import com.oop.lab5.R

class CuboidShape(private val context: Context):
    Shape(context),
    LineShapeInterface,
    RectShapeInterface {
    override val name = context.getString(R.string.cuboid)

    override fun isValid(): Boolean {
        return (startX != endX || startY != endY)
    }

    override fun getInstance(): Shape {
        return CuboidShape(context).also {
            it.associatedIds.putAll(this.associatedIds)
        }
    }

    override fun show(canvas: Canvas, outlinePaint: Paint, fillingPaint: Paint?) {
        val frontRect = RectF(startX, startY, endX, endY)
        rectShapeShow(context, canvas, outlinePaint, null, frontRect)
        val offset = 100F
        val backRect = RectF(frontRect).apply {
            offset(offset, -offset)
        }
        rectShapeShow(context, canvas, outlinePaint, null, backRect)
        frontRect.sort()
        backRect.sort()
        lineShapeShow(context, canvas, outlinePaint,
            PointF(frontRect.right, frontRect.top),
            PointF(backRect.right, backRect.top)
        )
        lineShapeShow(context, canvas, outlinePaint,
            PointF(frontRect.right, frontRect.bottom),
            PointF(backRect.right, backRect.bottom)
        )
        lineShapeShow(context, canvas, outlinePaint,
            PointF(frontRect.left, frontRect.bottom),
            PointF(backRect.left, backRect.bottom)
        )
        lineShapeShow(context, canvas, outlinePaint,
            PointF(frontRect.left, frontRect.top),
            PointF(backRect.left, backRect.top)
        )
    }

    override fun showDefault(canvas: Canvas) {
        show(canvas, getOutlinePaint("default"), null)
    }

    override fun showSelected(canvas: Canvas) {
        show(canvas, getOutlinePaint("selected"), null)
    }
}
