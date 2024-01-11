package com.oop.lab5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.oop.lab5.my_editor.MyEditor
import com.oop.lab5.my_table.MyTable
import com.oop.lab5.file_manager.FileManager
import com.oop.lab5.main_toolbar.MainToolbar
import com.oop.lab5.objects_toolbar.ObjectsToolbar
import com.oop.lab5.paint_view.PaintView
import com.oop.lab5.shape.Shape

class Lab5 : AppCompatActivity() {
    private lateinit var editor: MyEditor
    private lateinit var table: MyTable
    private lateinit var fileManager: FileManager
    private lateinit var mainToolbar: MainToolbar
    private lateinit var objectsToolbar: ObjectsToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Стартові налаштування MyTable
        table = MyTable()
        table.setOnHideTableListener { hideTable() }
        table.setOnSelectRowListener { rowIndex -> editor.selectShape(rowIndex) }
        table.setOnCancelRowsListener { rowsIndices -> editor.cancelShapes(rowsIndices) }
        table.setOnDeleteRowsListener { rowsIndices -> editor.deleteShapes(rowsIndices) }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.table_container, table)
            .hide(table)
            .commit()

        // Стартові налаштування MyEditor
        editor = MyEditor.getInstance()
        editor.onCreate(this)
        val paintView = findViewById<PaintView>(R.id.paint_view)
        paintView.handler = editor
        editor.paintUtils = paintView
        editor.setOnNewShapeListener { shape ->
            table.addRow(editor.serializeShape(shape))
        }
        editor.setOnUndoListener { table.onUndo() }
        editor.setOnClearAllListener { table.onClearAll() }

        // Стартові налаштування MainToolbar
        mainToolbar = findViewById(R.id.main_toolbar)
        mainToolbar.onCreate(editor)
        mainToolbar.setFileListeners(
            { fileManager.files(supportFragmentManager) },
            { fileManager.save() },
            { fileManager.saveAs(supportFragmentManager) }
        )
        mainToolbar.setTableListener {
            if (!table.isDisplayed) showTable()
            else hideTable()
        }
        mainToolbar.setObjListeners(::selectObj, ::cancelObj)

        // Стартові налаштування ObjectsToolbar
        objectsToolbar = findViewById(R.id.objects_toolbar)
        objectsToolbar.onCreate(editor)
        objectsToolbar.setObjListeners(::selectObj, ::cancelObj)

        // Стартові налаштування FileManager
        fileManager = FileManager(this)
        fileManager.onCreate { fileName ->
            mainToolbar.setFileName(fileName)
        }
        fileManager.setOnFileListeners(
            { newFileName ->
                mainToolbar.setFileName(newFileName)
                editor.serializeDrawing()
            },
            { fileName, serializedDrawing ->
                mainToolbar.setFileName(fileName)
                editor.deserializeDrawing(serializedDrawing)
            },
            { editor.serializeDrawing() },
            { _, newFileName ->
                if (newFileName != null) {
                    mainToolbar.setFileName(newFileName)
                    if (!editor.isDrawingEmpty()) editor.clearAll()
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (table.isDisplayed) hideTable()
    }

    private fun showTable() {
        table.isDisplayed = true
        supportFragmentManager
            .beginTransaction()
            .show(table)
            .commit()
        mainToolbar.onShowTable()
    }

    private fun hideTable() {
        table.isDisplayed = false
        supportFragmentManager
            .beginTransaction()
            .hide(table)
            .commit()
        mainToolbar.onHideTable()
    }

    private fun selectObj(shape: Shape) {
        mainToolbar.onSelectObj(shape)
        objectsToolbar.onSelectObj(shape)
        editor.start(shape)
    }

    private fun cancelObj() {
        mainToolbar.onCancelObj()
        objectsToolbar.onCancelObj()
        editor.close()
    }
}
