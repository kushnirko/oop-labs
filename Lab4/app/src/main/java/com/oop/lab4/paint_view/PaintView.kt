package com.oop.lab4.paint_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.oop.lab4.my_editor.PaintMessagesHandler

class PaintView(context: Context, attrs: AttributeSet?):
    View(context, attrs),
    PaintUtils {
    lateinit var handler: PaintMessagesHandler

    override lateinit var drawnShapesCanvas: Canvas
    override lateinit var rubberTraceCanvas: Canvas

    private lateinit var drawnShapesBitmap: Bitmap
    private lateinit var rubberTraceBitmap: Bitmap

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawnShapesBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawnShapesCanvas = Canvas(drawnShapesBitmap)
        rubberTraceBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        rubberTraceCanvas = Canvas(rubberTraceBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!handler.isRubberTraceModeOn) {
            handler.onPaint()
            canvas.drawBitmap(drawnShapesBitmap, 0F, 0F, null)
        } else {
            canvas.drawBitmap(drawnShapesBitmap, 0F, 0F, null)
            canvas.drawBitmap(rubberTraceBitmap, 0F, 0F, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> handler.onFingerTouch(x, y)
            MotionEvent.ACTION_MOVE -> handler.onFingerMove(x, y)
            MotionEvent.ACTION_UP -> handler.onFingerRelease()
        }
        return true
    }

    override fun repaint() {
        invalidate()
    }

    override fun clearCanvas(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY)
    }
}
