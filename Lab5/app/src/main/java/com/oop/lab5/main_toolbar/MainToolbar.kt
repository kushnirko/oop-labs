package com.oop.lab5.main_toolbar

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.oop.lab5.R

import com.oop.lab5.my_editor.MyEditor
import com.oop.lab5.shape.Shape
import com.oop.lab5.tooltip.Tooltip

class MainToolbar(context: Context, attrs: AttributeSet?):
    Toolbar(context, attrs) {
    private lateinit var editor: MyEditor

    private lateinit var optionsMenu: PopupMenu
    private lateinit var fileSubmenu: PopupMenu
    private lateinit var objSubmenu: PopupMenu
    private lateinit var objSubmenuItems: Array<MenuItem>
    private lateinit var btnTable: ImageButton

    private lateinit var fileNameView: TextView

    private lateinit var onShowHideTableListener: () -> Unit

    private lateinit var onFilesListener: () -> Unit
    private lateinit var onSaveListener: () -> Unit
    private lateinit var onSaveAsListener: () -> Unit

    private lateinit var onSelectObjListener: (Shape) -> Unit
    private lateinit var onCancelObjListener: () -> Unit

    fun onCreate(editor: MyEditor) {
        this.editor = editor
        fileNameView = findViewById(R.id.current_file_name)
        val btnUndo = findViewById<ImageButton>(R.id.btn_undo)
        btnUndo.setOnClickListener {
            this.editor.undo()
        }
        val btnClearAll = findViewById<ImageButton>(R.id.btn_clear_all)
        btnClearAll.setOnClickListener {
            this.editor.clearAll()
        }
        btnTable = findViewById(R.id.btn_table)
        btnTable.setOnClickListener { onShowHideTableListener() }
        val btnOptions = findViewById<ImageButton>(R.id.btn_options)
        btnOptions.setOnClickListener {
            optionsMenu.show()
        }
        optionsMenu = createOptionsMenu(btnOptions)
        fileSubmenu = createFileSubmenu(btnOptions)
        objSubmenu = createObjSubmenu(btnOptions)
        objSubmenuItems = arrayOf(
            objSubmenu.menu.findItem(R.id.item_point),
            objSubmenu.menu.findItem(R.id.item_line),
            objSubmenu.menu.findItem(R.id.item_rectangle),
            objSubmenu.menu.findItem(R.id.item_ellipse),
            objSubmenu.menu.findItem(R.id.item_segment),
            objSubmenu.menu.findItem(R.id.item_cuboid),
        )
        for (index in objSubmenuItems.indices) {
            val shape = editor.shapes[index]
            val item = objSubmenuItems[index]
            shape.associatedIds["objSubmenuItem"] = item.itemId
        }
    }

    private fun createOptionsMenu(anchor: View): PopupMenu {
        val popupMenu = PopupMenu(context, anchor)
        popupMenu.menuInflater.inflate(R.menu.main_toolbar_options_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.file -> {
                    fileSubmenu.show()
                    true
                }
                R.id.objects -> {
                    objSubmenu.show()
                    true
                }
                R.id.info -> {
                    Tooltip(context)
                        .create("Ви натиснули кнопку\n\"Довідка\"")
                        .display()
                    true
                }
                else -> {
                    false
                }
            }
        }
        return popupMenu
    }

    private fun createFileSubmenu(anchor: View): PopupMenu {
        val popupMenu = PopupMenu(context, anchor)
        popupMenu.menuInflater.inflate(R.menu.main_toolbar_file_submenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.files -> {
                    onFilesListener()
                    true
                }
                R.id.save -> {
                    onSaveListener()
                    true
                }
                R.id.save_as -> {
                    onSaveAsListener()
                    true
                }
                else -> {
                    false
                }
            }
        }
        return popupMenu
    }

    private fun createObjSubmenu(anchor: View): PopupMenu {
        val popupMenu = PopupMenu(context, anchor)
        popupMenu.menuInflater.inflate(R.menu.main_toolbar_objects_submenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { clickedItem ->
            for (index in objSubmenuItems.indices) {
                val item = objSubmenuItems[index]
                if (item == clickedItem) {
                    if (!item.isChecked) {
                        val shape = editor.shapes[index]
                        onSelectObjListener(shape.getInstance())
                    } else {
                        onCancelObjListener()
                    }
                }
            }
            true
        }
        return popupMenu
    }

    fun setTableListener(listener: () -> Unit) {
        onShowHideTableListener = listener
    }

    fun onShowTable () {
        val iconColor = context.getColor(R.color.on_main_toolbar_selected_btn_icon_color)
        btnTable.colorFilter = PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
    }

    fun onHideTable() {
        val iconColor = context.getColor(R.color.on_main_toolbar_color)
        btnTable.colorFilter = PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
    }

    fun setFileListeners(
        filesListener: () -> Unit,
        saveListener: () -> Unit,
        saveAsListener: () -> Unit
    ) {
        onFilesListener = filesListener
        onSaveListener = saveListener
        onSaveAsListener = saveAsListener
    }

    fun setFileName(fileName: String) {
        val maxFileNameLength = 12
        fileNameView.text =
            if (fileName.length <= maxFileNameLength) {
                fileName
            } else {
                "${fileName.substring(0..<maxFileNameLength)}..."
            }
    }

    fun setObjListeners(
        onSelectListener: (Shape) -> Unit,
        onCancelListener: () -> Unit
    ) {
        onSelectObjListener = onSelectListener
        onCancelObjListener = onCancelListener
    }

    fun onSelectObj(shape: Shape) {
        editor.currentShape?.let {
            val id = it.associatedIds["objSubmenuItem"]
            val item = objSubmenu.menu.findItem(id!!)
            item.isChecked = false
        }
        val id = shape.associatedIds["objSubmenuItem"]
        val item = objSubmenu.menu.findItem(id!!)
        item.isChecked = true
    }

    fun onCancelObj() {
        editor.currentShape?.let {
            val id = it.associatedIds["objSubmenuItem"]
            val item = objSubmenu.menu.findItem(id!!)
            item.isChecked = false
        }
    }
}
