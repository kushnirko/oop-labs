package com.oop.lab1

// Імпорт пакетів системи Android
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

// Імпорт пакетів модуля 1
import com.oop.lab1.module1.Module1
import com.oop.lab1.module1.Module1Interface

// Імпорт пакетів модуля 2
import com.oop.lab1.module2.Module2
import com.oop.lab1.module2.Module2Interface

// Імпорт пакетів модуля 3
import com.oop.lab1.module3.Module3
import com.oop.lab1.module3.Module3Interface

// Оголошення констант для позначення подій натискання кнопок
//                BTN_CANCEL    = 0
private const val BTN_CONFIRM   = 1
private const val BTN_THEN      = 2
private const val BTN_BACK      = 3

class Lab1 : AppCompatActivity(),
    Module1Handler,
    Module2Handler,
    Module3Handler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_lab1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_work1_menu_lab1 -> {
                runModule1()
                true
            }
            R.id.btn_work2_menu_lab1 -> {
                runModule2()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun runModule1() {
        val module1: Module1Interface = Module1()
        module1.run(this, supportFragmentManager, this)
    }
    private fun runModule2() {
        val module2: Module2Interface = Module2()
        module2.run(this, supportFragmentManager, this)
    }

    private fun runModule3() {
        val module3: Module3Interface = Module3()
        module3.run(this, supportFragmentManager, this)
    }

    override fun handleModule1Result(pressedButton: Int, result: Int?) {
        if (pressedButton == BTN_CONFIRM) {
            val textView = findViewById<TextView>(R.id.selected_number_lab1)
            if (result != null) {
                textView.text = "$result"
                textView.textSize = 50f
                val textColor = ContextCompat.getColor(this, R.color.black)
                textView.setTextColor(textColor)
            } else {
                textView.text = "На жаль, ви не вибрали значення, спробуйте ще"
                textView.textSize = 20f
                val textColor = ContextCompat.getColor(this, R.color.red)
                textView.setTextColor(textColor)
                textView.gravity = Gravity.CENTER
            }
        }
    }

    override fun handleModule2Result(pressedButton: Int) {
        if (pressedButton == BTN_THEN) {
            runModule3()
        }
    }

    override fun handleModule3Result(pressedButton: Int) {
        when (pressedButton) {
            BTN_BACK -> {
                runModule2()
            }
            BTN_CONFIRM -> {
                val text = "Ви натиснули кнопку \"Так\""
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(this, text, duration).show()
            }
        }
    }
}
