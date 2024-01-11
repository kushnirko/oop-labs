package com.oop.lab5.objects_toolbar

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.oop.lab5.R

import com.oop.lab5.my_editor.MyEditor
import com.oop.lab5.shape.Shape

class ObjectsToolbar(context: Context, attrs: AttributeSet?):
    Toolbar(context, attrs) {
    private lateinit var editor: MyEditor
    private lateinit var objButtons: Array<ObjectButton>

    private lateinit var onSelectObjListener: (Shape) -> Unit
    private lateinit var onCancelObjListener: () -> Unit

    fun onCreate(editor: MyEditor) {
        this.editor = editor
        objButtons = arrayOf(
            findViewById(R.id.btn_point),
            findViewById(R.id.btn_line),
            findViewById(R.id.btn_rectangle),
            findViewById(R.id.btn_ellipse),
            findViewById(R.id.btn_segment),
            findViewById(R.id.btn_cuboid),
        )
        for (index in objButtons.indices) {
            val shape = editor.shapes[index]
            val button = objButtons[index]
            shape.associatedIds["objButton"] = button.id
        }
    }

    fun setObjListeners(
        onSelectListener: (Shape) -> Unit,
        onCancelListener: () -> Unit
    ) {
        onSelectObjListener = onSelectListener
        onCancelObjListener = onCancelListener

        for (index in objButtons.indices) {
            val button = objButtons[index]
            val shape = editor.shapes[index]
            button.onCreate(shape)
            button.setObjListeners(onSelectObjListener, onCancelObjListener)
        }
    }

    fun onSelectObj(shape: Shape) {
        editor.currentShape?.let {
            val id = it.associatedIds["objButton"]
            val button = findViewById<ObjectButton>(id!!)
            button.onCancelObj()
        }
        val id = shape.associatedIds["objButton"]
        val button = findViewById<ObjectButton>(id!!)
        button.onSelectObj()
    }

    fun onCancelObj() {
        editor.currentShape?.let {
            val id = it.associatedIds["objButton"]
            val button = findViewById<ObjectButton>(id!!)
            button.onCancelObj()
        }
    }
}
