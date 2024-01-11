package com.oop.lab4.tooltip

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.oop.lab4.R

class Tooltip(context: Context, attrs: AttributeSet?): View(context, attrs) {
    private lateinit var tooltip: Snackbar

    fun create(parent: View, text: String): Tooltip {
        val displayDuration = Snackbar.LENGTH_LONG
        tooltip = Snackbar.make(parent, "", displayDuration)

        val backgroundColor = context.getColor(R.color.transparent)
        tooltip.view.setBackgroundColor(backgroundColor)

        val layout = tooltip.view as Snackbar.SnackbarLayout
        val view = inflate(context, R.layout.tooltip, null)
        layout.addView(view)

        val textView = view.findViewById<TextView>(R.id.tooltip_text)
        textView.text = text

        val btnHide = view.findViewById<Button>(R.id.tooltip_hide)
        btnHide.setOnClickListener {
            val textColor = context.getColor(R.color.tooltip_bnt_clicked_text_color)
            btnHide.setTextColor(textColor)
            hide()
        }
        return this
    }

    fun hide() {
        tooltip.dismiss()
    }

    fun show() {
        tooltip.show()
    }
}
