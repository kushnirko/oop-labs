package com.oop.lab5.my_table

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.oop.lab5.R

class MyTable: Fragment(R.layout.table) {
    var isDisplayed = false

    private lateinit var scrollView: ScrollView
    private lateinit var tableLayout: TableLayout
    
    private lateinit var bottomView: LinearLayout
    private lateinit var defaultBottomView: LinearLayout
    private lateinit var selectBottomView: LinearLayout
    
    private val selectedRowsIndices = mutableListOf<Int>()

    private var onHideTableListener: (() -> Unit)? = null
    private var onSelectRowListener: ((Int) -> Unit)? = null
    private var onCancelRowsListener: ((List<Int>) -> Unit)? = null
    private var onDeleteRowsListener: ((List<Int>) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scrollView = view.findViewById(R.id.table_scroll_view)
        tableLayout = view.findViewById(R.id.table_table_layout)
        bottomView = view.findViewById(R.id.files_dialog_bottom_view)

        defaultBottomView = LinearLayout(context)
        layoutInflater.inflate(
            R.layout.table_default_bottom_view, defaultBottomView, true
        )
        val buttonHide = defaultBottomView.findViewById<Button>(R.id.files_dialog_btn_hide)
        buttonHide.setOnClickListener {
            onHideTableListener?.invoke()
        }

        selectBottomView = LinearLayout(context)
        layoutInflater.inflate(
            R.layout.table_select_bottom_view, selectBottomView, true
        )
        val buttonCancel = selectBottomView.findViewById<Button>(R.id.files_dialog_btn_open)
        buttonCancel.setOnClickListener {
            cancelRows(selectedRowsIndices.toList())
        }
        val buttonDelete = selectBottomView.findViewById<Button>(R.id.files_dialog_btn_delete)
        buttonDelete.setOnClickListener {
            val indices = selectedRowsIndices.toList()
            deleteRows(indices)
            onDeleteRowsListener?.invoke(indices)
        }

        bottomView.addView(defaultBottomView)
    }

    fun addRow(serializedShape: String) {
        val data = serializedShape.dropLast(1).split("\t")
        val fields = object {
            val name = data[0]
            val x1 = data[1]
            val y1 = data[2]
            val x2 = data[3]
            val y2 = data[4]
        }
        val row = TableRow(context)
        layoutInflater.inflate(R.layout.table_row, row, true)
        row.findViewById<TextView>(R.id.table_shape_name).text = fields.name
        row.findViewById<TextView>(R.id.table_x1).text = fields.x1
        row.findViewById<TextView>(R.id.table_y1).text = fields.y1
        row.findViewById<TextView>(R.id.table_x2).text = fields.x2
        row.findViewById<TextView>(R.id.table_y2).text = fields.y2

        row.setOnClickListener {
            val rowIndex = tableLayout.indexOfChild(it)
            if (!selectedRowsIndices.contains(rowIndex)) {
                selectRow(rowIndex)
            } else {
                cancelRows(listOf(rowIndex))
            }
        }
        tableLayout.addView(row)
        val firstChild = tableLayout.children.first()
        if (firstChild is TextView) {
            tableLayout.removeView(firstChild)
        }
        setDefaultRowBgColor(tableLayout.indexOfChild(row))
        scrollView.scrollToDescendant(row)
    }

    private fun selectRow(index: Int) {
        if (selectedRowsIndices.isEmpty()) {
            bottomView.removeView(defaultBottomView)
            bottomView.addView(selectBottomView)
        }
        selectedRowsIndices.add(index)
        setSelectedRowBgColor(index)
        onSelectRowListener?.invoke(index)
    }

    private fun cancelRows(indices: List<Int>) {
        for (index in indices) {
            selectedRowsIndices.remove(index)
            setDefaultRowBgColor(index)
        }
        if (selectedRowsIndices.isEmpty()) {
            bottomView.removeView(selectBottomView)
            bottomView.addView(defaultBottomView)
        }
        onCancelRowsListener?.invoke(indices)
    }

    fun deleteRows(indices: List<Int>) {
        for (index in indices.sorted().sortedDescending()) {
            selectedRowsIndices.remove(index)
            val row = tableLayout.getChildAt(index)
            tableLayout.removeView(row)
        }
        (indices.min()..<tableLayout.childCount).forEach {
            setDefaultRowBgColor(it)
        }
        if (tableLayout.childCount == 0) {
            if (bottomView.children.first() == selectBottomView) {
                bottomView.removeView(selectBottomView)
                bottomView.addView(defaultBottomView)
            }
            val textView = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    resources.getDimension(R.dimen.table_content_height).toInt()
                )
                text = "Полотно порожнє"
                textSize = 20F
                setTypeface(null, Typeface.ITALIC)
                gravity = Gravity.CENTER
            }
            tableLayout.addView(textView)
        } else if (selectedRowsIndices.isEmpty()) {
            if (bottomView.children.first() == selectBottomView) {
                bottomView.removeView(selectBottomView)
                bottomView.addView(defaultBottomView)
            }
        }
    }

    private fun setDefaultRowBgColor(index: Int) {
        val row = tableLayout.getChildAt(index)
        row.setBackgroundColor(
            if (index % 2 == 0) {
                requireActivity().getColor(R.color.table_default_row_bg_color_1)
            } else {
                requireActivity().getColor(R.color.table_default_row_bg_color_2)
            }
        )
    }

    private fun setSelectedRowBgColor(index: Int) {
        val row = tableLayout.getChildAt(index)
        row.setBackgroundColor(
            if (index % 2 == 0) {
                requireActivity().getColor(R.color.table_selected_row_bg_color_1)
            } else {
                requireActivity().getColor(R.color.table_selected_row_bg_color_2)
            }
        )
    }

    fun onUndo() {
        deleteRows(listOf(tableLayout.childCount - 1))
    }

    fun onClearAll() {
        deleteRows((0..<tableLayout.childCount).toList())
    }

    fun setOnHideTableListener(listener: () -> Unit) {
        onHideTableListener = listener
    }

    fun setOnSelectRowListener(listener: (Int) -> Unit) {
        onSelectRowListener = listener
    }

    fun setOnCancelRowsListener(listener: (List<Int>) -> Unit) {
        onCancelRowsListener = listener
    }

    fun setOnDeleteRowsListener(listener: (List<Int>) -> Unit) {
        onDeleteRowsListener = listener
    }
}
