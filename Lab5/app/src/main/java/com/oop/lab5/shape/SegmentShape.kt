package com.oop.lab5.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.oop.lab5.R
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SegmentShape(private val context: Context):
    Shape(context),
    LineShapeInterface,
    EllipseShapeInterface {
    override val name = context.getString(R.string.segment)

    override fun isValid(): Boolean {
        return (startX != endX || startY != endY)
    }

    override fun getInstance(): Shape {
        return SegmentShape(context).also {
            it.associatedIds.putAll(this.associatedIds)
        }
    }

    override fun show(canvas: Canvas, outlinePaint: Paint, fillingPaint: Paint?) {
        if (!isValid()) return
        val ellipseRadius = 50F
        val startEllipseCenter = PointF(startX, startY)
        val endEllipseCenter = PointF(endX, endY)

        val dx = abs(endX - startX)
        val dy = abs(endY - startY)
        val distance = sqrt(dx * dx + dy * dy)
        val angle = acos(dx / distance)
        val offset = PointF(ellipseRadius * cos(angle), ellipseRadius * sin(angle))

        val startTangentPoint = PointF()
        val endTangentPoint = PointF()
        if (startX < endX) {
            startTangentPoint.x = startX + offset.x
            endTangentPoint.x = endX - offset.x
        } else {
            startTangentPoint.x = startX - offset.x
            endTangentPoint.x = endX + offset.x
        }
        if (startY < endY) {
            startTangentPoint.y = startY + offset.y
            endTangentPoint.y = endY - offset.y
        } else {
            startTangentPoint.y = startY - offset.y
            endTangentPoint.y = endY + offset.y
        }
        lineShapeShow(context, canvas, outlinePaint, startTangentPoint, endTangentPoint)
        ellipseShapeShow(context, canvas, outlinePaint, null,
            startEllipseCenter, ellipseRadius)
        ellipseShapeShow(context, canvas, outlinePaint, null,
            endEllipseCenter, ellipseRadius)
    }

    override fun showDefault(canvas: Canvas) {
        show(canvas, getOutlinePaint("default"), null)
    }

    override fun showSelected(canvas: Canvas) {
        show(canvas, getOutlinePaint("selected"), null)
    }
}
