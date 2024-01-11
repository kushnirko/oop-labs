package com.oop.lab1.module3

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
import com.oop.lab1.Module3Handler

// Оголошення констант для позначення подій натискання кнопок
private const val BTN_CANCEL    = 0
private const val BTN_CONFIRM   = 1
private const val BTN_BACK      = 3

class Module3 : DialogFragment(), Module3Interface {
    // Змінна, у яку присвоїться контекст головної активності
    private lateinit var activityContext: Context

    // Змінна, що зберігатиме вказівник на інтерфейс класу, який має callback-фунцію
    private lateinit var resultListener: Module3Handler

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activityContext)
        builder.setTitle(R.string.dialog_name_module3)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_module3, null)
        builder.setView(view)

        val buttonBack = view.findViewById<Button>(R.id.btn_back_module3)
        buttonBack.setOnClickListener {
            resultListener.handleModule3Result(BTN_BACK)
            dismiss()
        }

        val buttonConfirm = view.findViewById<Button>(R.id.btn_confirm_module3)
        buttonConfirm.setOnClickListener {
            resultListener.handleModule3Result(BTN_CONFIRM)
        }

        val buttonCancel = view.findViewById<Button>(R.id.btn_cancel_module3)
        buttonCancel.setOnClickListener {
            resultListener.handleModule3Result(BTN_CANCEL)
            dismiss()
        }

        return builder.create()
    }

    // Інтерфейсний метод, за допомогою якого головна активність взаємодіятиме з модулем
    override fun run(context: Context, manager: FragmentManager, handler: Module3Handler) {
        val module3 = Module3()
        module3.activityContext = context
        module3.resultListener = handler
        module3.show(manager, "dialog_module2")
    }
}
