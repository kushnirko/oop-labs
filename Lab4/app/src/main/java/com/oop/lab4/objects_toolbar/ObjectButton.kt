package com.oop.lab4.objects_toolbar

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.MotionEvent
import com.oop.lab4.R

import com.oop.lab4.shape.Shape
import com.oop.lab4.tooltip.Tooltip

class ObjectButton(context: Context, attrs: AttributeSet?):
    androidx.appcompat.widget.AppCompatImageButton(context, attrs) {
    private lateinit var shape: Shape

    private var isObjSelected = false
    private lateinit var onObjSelectListener: (Shape) -> Unit
    private lateinit var onObjCancelListener: () -> Unit

    private val selectTooltip = Tooltip(context, attrs)
    private val cancelTooltip = Tooltip(context, attrs)

    private val timeOfLongPress = 1000
    private var pressStartTime: Long = 0
    private var pressEndTime: Long = 0

    fun onCreate(shape: Shape) {
        this.shape = shape
        val selectTooltipText = "Вибрати об\'єкт\n\"${shape.name}\""
        selectTooltip.create(this, selectTooltipText)
        val cancelTooltipText = "Вимкнути режим\nредагування"
        cancelTooltip.create(this, cancelTooltipText)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                markPressed()
                pressStartTime = System.currentTimeMillis()
            }
            MotionEvent.ACTION_UP -> {
                pressEndTime = System.currentTimeMillis()
                val pressDuration = pressEndTime - pressStartTime
                if (pressDuration < timeOfLongPress) {
                    performClick()
                } else {
                    performLongClick()
                }
                pressStartTime = 0
                pressEndTime = 0
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        if (!isObjSelected) {
            onObjSelectListener(shape.getInstance())
        } else {
            onObjCancelListener()
        }
        return true
    }

    override fun performLongClick(): Boolean {
        super.performLongClick()
        if (!isObjSelected) {
            markNotPressed()
            selectTooltip.show()
        } else {
            markSelected()
            cancelTooltip.show()
        }
        return true
    }

    private fun markPressed() {
        val backgroundColorId = R.color.pressed_btn_background_color
        backgroundTintList = context.getColorStateList(backgroundColorId)
    }

    private fun markNotPressed() {
        val backgroundColorId = R.color.transparent
        backgroundTintList = context.getColorStateList(backgroundColorId)
    }

    private fun markSelected() {
        val backgroundColorId = R.color.selected_btn_background_color
        backgroundTintList = context.getColorStateList(backgroundColorId)
        val iconColor = context.getColor(R.color.selected_btn_icon_color)
        colorFilter = PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
    }

    private fun markNotSelected() {
        val backgroundColorId = R.color.transparent
        backgroundTintList = context.getColorStateList(backgroundColorId)
        val iconColor = context.getColor(R.color.on_objects_toolbar_color)
        colorFilter = PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
    }

    fun setObjListeners(
        onSelectListener: (Shape) -> Unit,
        onCancelListener: () -> Unit
    ) {
        onObjSelectListener = onSelectListener
        onObjCancelListener = onCancelListener
    }

    fun onObjSelect() {
        isObjSelected = true
        markSelected()
    }

    fun onObjCancel() {
        isObjSelected = false
        markNotSelected()
    }
}
