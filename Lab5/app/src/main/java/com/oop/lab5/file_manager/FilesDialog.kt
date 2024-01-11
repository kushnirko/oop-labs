package com.oop.lab5.file_manager

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.oop.lab5.R

class FilesDialog : DialogFragment(R.layout.files_dialog) {
    private lateinit var tableLayout: TableLayout
    private lateinit var bottomView: LinearLayout
    private lateinit var defaultBottomView: LinearLayout
    private lateinit var selectBottomView: LinearLayout

    private var currentFileList = mutableListOf<String>()
    private var selectedRow = object {
        var view: TableRow? = null
        var fileName: String? = null
    }

    private lateinit var onOpenListener: (String) -> Unit
    private lateinit var onDeleteListener: (String) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tableLayout = view.findViewById(R.id.files_dialog_table_layout)
        tableLayout.removeAllViews()
        if (currentFileList.isNotEmpty()) {
            for (file in currentFileList) {
                val row = TableRow(context)
                layoutInflater.inflate(R.layout.files_dialog_row, row, true)
                row.findViewById<TextView>(R.id.files_dialog_row_name).text = file
                row.setOnClickListener {
                    val selectedRowView = selectedRow.view
                    if (selectedRowView != null) cancelRow()
                    if (selectedRowView != it) selectRow(it as TableRow)
                }
                tableLayout.addView(row)
            }
        } else {
            onEmptyDir()
        }

        bottomView = view.findViewById(R.id.files_dialog_bottom_view)
        defaultBottomView = LinearLayout(context)
        layoutInflater.inflate(
            R.layout.files_dialog_default_bottom_view,
            defaultBottomView, true
        )
        val buttonHide = defaultBottomView.findViewById<Button>(R.id.files_dialog_btn_hide)
        buttonHide.setOnClickListener { dismiss() }
        selectBottomView = LinearLayout(context)
        layoutInflater.inflate(
            R.layout.files_dialog_select_bottom_view,
            selectBottomView, true
        )
        val buttonOpen = selectBottomView.findViewById<Button>(R.id.files_dialog_btn_open)
        buttonOpen.setOnClickListener {
            onOpenListener(selectedRow.fileName!!)
            cancelRow()
            dismiss()
        }
        val buttonDelete = selectBottomView.findViewById<Button>(R.id.files_dialog_btn_delete)
        buttonDelete.setOnClickListener {
            deleteRow()
        }
        bottomView.addView(defaultBottomView)
    }

    fun display(manager: FragmentManager, fileList: Array<String>?) {
        currentFileList.clear()
        if (!fileList.isNullOrEmpty()) {
            currentFileList.addAll(fileList)
        }
        show(manager, "files_dialog")
    }

    private fun onEmptyDir() {
        val textView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                resources.getDimension(R.dimen.files_dialog_content_height).toInt()
            )
            gravity = Gravity.CENTER
            text = context.getString(R.string.files_dialog_default_text)
            textSize = 20F
            setTypeface(null, Typeface.ITALIC)
        }
        tableLayout.addView(textView)
    }

    private fun selectRow(row: TableRow) {
        bottomView.removeView(defaultBottomView)
        bottomView.addView(selectBottomView)

        row.setBackgroundColor(
            requireActivity().getColor(R.color.files_dialog_selected_row_bg_color)
        )
        selectedRow.view = row
        selectedRow.fileName = row
            .findViewById<TextView>(R.id.files_dialog_row_name)
            .text
            .toString()
    }

    private fun cancelRow() {
        bottomView.removeView(selectBottomView)
        bottomView.addView(defaultBottomView)

        selectedRow.view!!.setBackgroundColor(
            requireActivity().getColor(R.color.files_dialog_default_row_bg_color)
        )
        selectedRow.view = null
        selectedRow.fileName = null
    }

    private fun deleteRow() {
        bottomView.removeView(selectBottomView)
        bottomView.addView(defaultBottomView)

        currentFileList.remove(selectedRow.fileName)
        tableLayout.removeView(selectedRow.view)
        onDeleteListener(selectedRow.fileName!!)
        selectedRow.view = null
        selectedRow.fileName = null
        if (currentFileList.isEmpty()) onEmptyDir()
    }

    fun setOnFileListeners(
        openListener: (String) -> Unit,
        deleteListener: (String) -> Unit
    ) {
        onOpenListener = openListener
        onDeleteListener = deleteListener
    }
}
