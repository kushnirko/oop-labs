package com.oop.lab4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.oop.lab4.shape.Shape
import com.oop.lab4.my_editor.MyEditor
import com.oop.lab4.paint_view.PaintView
import com.oop.lab4.main_toolbar.MainToolbar
import com.oop.lab4.objects_toolbar.ObjectsToolbar

class Lab4 : AppCompatActivity() {
    private lateinit var editor: MyEditor
    private lateinit var mainToolbar: MainToolbar
    private lateinit var objectsToolbar: ObjectsToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        editor = MyEditor(this)

        mainToolbar = findViewById(R.id.main_toolbar)
        mainToolbar.onCreate(editor)
        mainToolbar.setObjListeners(::onObjSelect, ::onObjCancel)

        objectsToolbar = findViewById(R.id.objects_toolbar)
        objectsToolbar.onCreate(editor)
        objectsToolbar.setObjListeners(::onObjSelect, ::onObjCancel)

        val paintView = findViewById<PaintView>(R.id.paint_view)
        paintView.handler = editor
        editor.paintUtils = paintView
    }

    private fun onObjSelect(shape: Shape) {
        mainToolbar.onObjSelect(shape)
        objectsToolbar.onObjSelect(shape)
        editor.start(shape)
    }

    private fun onObjCancel() {
        mainToolbar.onObjCancel()
        objectsToolbar.onObjCancel()
        editor.close()
    }
}
