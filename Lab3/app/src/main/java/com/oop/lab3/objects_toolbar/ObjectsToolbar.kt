package com.oop.lab3.objects_toolbar

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.oop.lab3.R

import com.oop.lab3.shape_editor.ShapeObjectsEditor
import com.oop.lab3.shape.Shape

class ObjectsToolbar(context: Context, attrs: AttributeSet?):
    Toolbar(context, attrs) {
    private lateinit var shapeObjEditor: ShapeObjectsEditor
    private lateinit var objButtons: Array<ObjectButton>

    private lateinit var onObjSelectListener: (Shape) -> Unit
    private lateinit var onObjCancelListener: () -> Unit

    fun onCreate(shapeObjEditor: ShapeObjectsEditor) {
        this.shapeObjEditor = shapeObjEditor
        objButtons = arrayOf(
            findViewById(R.id.btn_point),
            findViewById(R.id.btn_line),
            findViewById(R.id.btn_rectangle),
            findViewById(R.id.btn_ellipse),
        )
        for (index in objButtons.indices) {
            val shape = shapeObjEditor.shapes[index]
            val button = objButtons[index]
            shape.associatedIds["objButton"] = button.id
        }
    }

    fun setObjListeners(
        onSelectListener: (Shape) -> Unit,
        onCancelListener: () -> Unit
    ) {
        onObjSelectListener = onSelectListener
        onObjCancelListener = onCancelListener

        for (index in objButtons.indices) {
            val button = objButtons[index]
            val shape = shapeObjEditor.shapes[index]
            button.onCreate(shape)
            button.setObjListeners(onObjSelectListener, onObjCancelListener)
        }
    }

    fun onObjSelect(shape: Shape) {
        shapeObjEditor.currentShape?.let {
            val id = it.associatedIds["objButton"]
            val button = findViewById<ObjectButton>(id!!)
            button.onObjCancel()
        }
        val id = shape.associatedIds["objButton"]
        val button = findViewById<ObjectButton>(id!!)
        button.onObjSelect()
    }

    fun onObjCancel() {
        shapeObjEditor.currentShape?.let {
            val id = it.associatedIds["objButton"]
            val button = findViewById<ObjectButton>(id!!)
            button.onObjCancel()
        }
    }
}
