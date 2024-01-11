package com.oop.lab2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.oop.lab2.shape_editor.ShapeObjectsEditorInterface

class PaintView(context: Context, attrs: AttributeSet?):
    View(context, attrs),
    PaintViewInterface {
    override val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 7f
    }
    override lateinit var shapeObjectsEditor: ShapeObjectsEditorInterface

    override lateinit var drawnShapesCanvas: Canvas
    override lateinit var rubberTraceCanvas: Canvas

    private var drawnShapesBitmap: Bitmap? = null
    private var rubberTraceBitmap: Bitmap? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (drawnShapesBitmap == null &&
            rubberTraceBitmap == null) {
            drawnShapesBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            drawnShapesCanvas = Canvas(drawnShapesBitmap!!)
            rubberTraceBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            rubberTraceCanvas = Canvas(rubberTraceBitmap!!)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!shapeObjectsEditor.isRubberTraceModeOn) {
            shapeObjectsEditor.onPaint()
            canvas.drawBitmap(drawnShapesBitmap!!, 0F, 0F, null)
        } else {
            canvas.drawBitmap(drawnShapesBitmap!!, 0F, 0F, null)
            canvas.drawBitmap(rubberTraceBitmap!!, 0F, 0F, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> shapeObjectsEditor.onFingerTouch(x, y)
            MotionEvent.ACTION_MOVE -> shapeObjectsEditor.onFingerMove(x, y)
            MotionEvent.ACTION_UP -> shapeObjectsEditor.onFingerRelease()
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
