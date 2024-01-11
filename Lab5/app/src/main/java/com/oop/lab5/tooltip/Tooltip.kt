package com.oop.lab5.tooltip

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.oop.lab5.R

class Tooltip(context: Context): View(context) {
    private lateinit var tooltip: Snackbar

    fun create(text: String): Tooltip {
        val activityView = (context as Activity).findViewById<View>(android.R.id.content)
        val displayDuration = Snackbar.LENGTH_LONG
        tooltip = Snackbar.make(activityView, "", displayDuration)

        val backgroundColor = context.getColor(R.color.transparent)
        tooltip.view.setBackgroundColor(backgroundColor)

        val layout = tooltip.view as ViewGroup
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

    fun display() {
        tooltip.show()
    }
}
