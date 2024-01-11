package com.oop.lab2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach

import com.oop.lab2.shape_editor.ShapeObjectsEditor
import com.oop.lab2.shape_editor.ShapeObjectsEditorInterface

class Lab2 : AppCompatActivity() {
    private var objectsPopupMenu: PopupMenu? = null
    private val shapeObjectsEditor: ShapeObjectsEditorInterface = ShapeObjectsEditor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val paintView = findViewById<PaintView>(R.id.paint_view)
        paintView.shapeObjectsEditor = shapeObjectsEditor
        shapeObjectsEditor.paintView = paintView
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_file -> {
                Toast
                    .makeText(this, "Ви натиснули кнопку \"Файл\"", Toast.LENGTH_SHORT)
                    .show()
                true
            }
            R.id.btn_objects -> {
                if (objectsPopupMenu == null) {
                    val btnObjects = findViewById<View>(R.id.btn_objects)
                    objectsPopupMenu = createObjectsPopupMenu(btnObjects)
                }
                showObjectsPopupMenu()
                true
            }
            R.id.btn_info -> {
                Toast
                    .makeText(this, "Ви натиснули кнопку \"Довідка\"", Toast.LENGTH_SHORT)
                    .show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun createObjectsPopupMenu(anchor: View): PopupMenu {
        val popupMenu = PopupMenu(this, anchor)
        popupMenu.menuInflater.inflate(R.menu.objects_popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            popupMenu.menu.forEach {
                if (it.isChecked) {
                    it.isChecked = false
                }
            }
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.btn_point -> {
                    shapeObjectsEditor.startPointEditor()
                    supportActionBar?.title = getString(R.string.point)
                    true
                }
                R.id.btn_line -> {
                    shapeObjectsEditor.startLineEditor()
                    supportActionBar?.title = getString(R.string.line)
                    true
                }
                R.id.btn_rectangle -> {
                    shapeObjectsEditor.startRectEditor()
                    supportActionBar?.title = getString(R.string.rectangle)
                    true
                }
                R.id.btn_ellipse -> {
                    shapeObjectsEditor.startEllipseEditor()
                    supportActionBar?.title = getString(R.string.ellipse)
                    true
                }
                else -> false
            }
        }

        return popupMenu
    }

    private fun showObjectsPopupMenu() {
        objectsPopupMenu?.show()
    }
}
