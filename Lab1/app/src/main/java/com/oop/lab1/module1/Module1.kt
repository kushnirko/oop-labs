package com.oop.lab1.module1

// Імпорт пакетів системи Android
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

// Імпорт ресурсів
import com.oop.lab1.R

// Імпорт інтерфейсу класу, який має callback-фунцію,
// що викликатиметься після закриття ділогового вікна та
// опрацьовуватиме результат, отриманий після виконання модуля
import com.oop.lab1.Module1Handler

// Оголошення констант для позначення подій натискання кнопок
private const val BTN_CANCEL    = 0
private const val BTN_CONFIRM   = 1

class Module1 : DialogFragment(), Module1Interface {
    // Змінна, через яку передамо результат,
    // отриманий у діалоговому вікні, до головного модуля
    private var result: Int? = null

    // Змінна, у яку присвоїться контекст головної активності
    private lateinit var activityContext: Context

    // Змінна, що зберігатиме вказівник на інтерфейс класу, який має callback-фунцію
    private lateinit var resultHandler: Module1Handler

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activityContext)
        builder.setTitle(R.string.dialog_name_module1)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_module1, null)
        builder.setView(view)

        val textView = view.findViewById<TextView>(R.id.current_number_module1)
        val seekBar = view.findViewById<SeekBar>(R.id.seek_bar_module1)
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                result = progress + 1
                textView.text = "$result"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                if (result == null) {
                    result = 1
                }
                textView.text = "$result"
                textView.textSize = 30f
                val textColor = ContextCompat.getColor(activityContext, R.color.black)
                textView.setTextColor(textColor)
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                textView.textSize = 20f
            }
        })

        val buttonConfirm = view.findViewById<Button>(R.id.btn_confirm_module1)
        buttonConfirm.setOnClickListener {
            resultHandler.handleModule1Result(BTN_CONFIRM, result)
            dismiss()
        }

        val buttonCancel = view.findViewById<Button>(R.id.btn_cancel_module1)
        buttonCancel.setOnClickListener {
            resultHandler.handleModule1Result(BTN_CANCEL, result)
            dismiss()
        }

        return builder.create()
    }

    // Інтерфейсний метод, за допомогою якого головна активність взаємодіятиме з модулем
    override fun run(context: Context, manager: FragmentManager, handler: Module1Handler) {
        val module1 = Module1()
        module1.activityContext = context
        module1.resultHandler = handler
        module1.show(manager, "dialog_module1")
    }
}
