package com.oop.lab1.module2

// Імпорт пакетів системи Android
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

// Імпорт ресурсів
import com.oop.lab1.R

// Імпорт інтерфейсу класу, який має callback-фунцію,
// що викликатиметься після закриття ділогового вікна та
// опрацьовуватиме результат, отриманий після виконання модуля
import com.oop.lab1.Module2Handler

// Оголошення констант для позначення подій натискання кнопок
private const val BTN_CANCEL    = 0
private const val BTN_THEN      = 2

class Module2 : DialogFragment(), Module2Interface {
    // Змінна, у яку присвоїться контекст головної активності
    private lateinit var activityContext: Context

    // Змінна, що зберігатиме вказівник на інтерфейс класу, який має callback-фунцію
    private lateinit var resultHandler: Module2Handler

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activityContext)
        builder.setTitle(R.string.dialog_name_module2)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_module2, null)
        builder.setView(view)

        val buttonThen = view.findViewById<Button>(R.id.btn_then_module2)
        buttonThen.setOnClickListener {
            resultHandler.handleModule2Result(BTN_THEN)
            dismiss()
        }

        val buttonCancel = view.findViewById<Button>(R.id.btn_cancel_module2)
        buttonCancel.setOnClickListener {
            resultHandler.handleModule2Result(BTN_CANCEL)
            dismiss()
        }

        return builder.create()
    }

    // Інтерфейсний метод, за допомогою якого головна активність взаємодіятиме з модулем
    override fun run(context: Context, manager: FragmentManager, handler: Module2Handler) {
        val module2 = Module2()
        module2.activityContext = context
        module2.resultHandler = handler
        module2.show(manager, "dialog_module2")
    }
}
