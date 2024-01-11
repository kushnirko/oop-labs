package com.oop.lab6

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class Dialog: DialogFragment() {
    private lateinit var onConfirmListener: (IntArray) -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.dialog_header_text)
        val view: View = layoutInflater.inflate(R.layout.dialog, null)
        builder.setView(view)

        val nEditText: EditText = view.findViewById(R.id.enter_n)
        val minEditText: EditText = view.findViewById(R.id.enter_min)
        val maxEditText: EditText = view.findViewById(R.id.enter_max)

        val btnCancel: Button = view.findViewById(R.id.dialog_cancel)
        btnCancel.setOnClickListener { dismiss() }

        val btnOkay: Button = view.findViewById(R.id.dialog_okay)
        btnOkay.setOnClickListener {
            val n = nEditText.text.toString()
            val min = minEditText.text.toString()
            val max = maxEditText.text.toString()
            val validationResult = validateInput(n, min, max)
            if (validationResult != null) {
                dismiss()
                val delayToDismiss = 100L
                Handler(Looper.getMainLooper()).postDelayed({
                    onConfirmListener(validationResult)
                }, delayToDismiss)
            }
        }

        return builder.create().apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    private fun validateInput(n: String, min: String, max: String): IntArray? {
        val parsedN = parseToInt("n", n) ?: return null
        if (parsedN <= 0) {
            showToast("n має бути більше 0")
            return null
        }
        val parsedMin = parseToInt("Min", min) ?: return null
        val parsedMax = parseToInt("Max", max) ?: return null
        if (parsedMin > parsedMax) {
            showToast("Min не може бути більшим за Max")
            return null
        }
        return intArrayOf(parsedN, parsedMin, parsedMax)
    }

    private fun parseToInt(key: String, value: String): Int? {
        return try {
            value.toInt()
        } catch (_: Exception) {
            if (value == "") showToast("Поле $key порожнє")
            else showToast("$key не є числом")
            null
        }
    }

    private fun showToast(text: String) {
        with(Toast(requireActivity())) {
            setText(text)
            duration = Toast.LENGTH_SHORT
            show()
        }
    }

    fun setOnConfirmListener (listener: (IntArray) -> Unit) {
        onConfirmListener = listener
    }
}
