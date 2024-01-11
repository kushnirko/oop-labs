package com.oop.lab5.file_manager

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.oop.lab5.R

import com.oop.lab5.tooltip.Tooltip

class CreateFileDialog: DialogFragment(R.layout.create_file_dialog) {
    private lateinit var editText: EditText
    private var hint = ""

    private lateinit var onCancelListener: () -> Unit
    private lateinit var onConfirmListener: (String) -> Pair<Boolean, String>

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

        editText = view.findViewById(R.id.enter_file_name)
        editText.hint = hint
        val buttonCancel = view.findViewById<Button>(R.id.files_dialog_btn_open)
        buttonCancel.setOnClickListener {
            editText.text.clear()
            onCancelListener()
            dismiss()
        }
        val buttonOkay = view.findViewById<Button>(R.id.files_dialog_btn_delete)
        buttonOkay.setOnClickListener {
            val text = editText.text.toString()
            editText.text.clear()
            val (isNameValid, message) = onConfirmListener(text)
            if (isNameValid) {
                dismiss()
                Tooltip(requireActivity()).create(message).display()
            } else {
                editText.setHintTextColor(Color.RED)
                editText.hint = message
            }
        }
    }

    fun display(manager: FragmentManager, nameHint: String) {
        hint = nameHint
        show(manager, "create_file_dialog")
    }

    fun setFileCreationListeners(
        cancelListener: () -> Unit,
        confirmListener: (String) -> Pair<Boolean, String>
    ) {
        onConfirmListener = confirmListener
        onCancelListener = cancelListener
    }
}
