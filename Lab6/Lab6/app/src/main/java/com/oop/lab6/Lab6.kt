package com.oop.lab6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Lab6 : AppCompatActivity() {
    private val object2PackageName = "com.oop.object2"
    private val object3PackageName = "com.oop.object3"

    private val object2SignalHandler = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                if (it.action == "OBJECT2_SEND_SIGNAL") {
                    val signal = it.getStringExtra("SIGNAL")
                    if (signal == "TASK_END_SUCCESS") {
                        val object3LaunchDelay = 200L
                        Handler(Looper.getMainLooper()).postDelayed({
                            launchAppWithSignal(object3PackageName, "START")
                        }, object3LaunchDelay)
                    } else {
                        showToast("Сталась помилка виконання $object2PackageName")
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val intentFilter = IntentFilter("OBJECT2_SEND_SIGNAL")
        registerReceiver(object2SignalHandler, intentFilter, RECEIVER_EXPORTED)

        val btnCompleteTask: Button = findViewById(R.id.complete_task)
        btnCompleteTask.setOnClickListener {
            val dialog = Dialog()
            dialog.setOnConfirmListener { data ->
                launchAppWithData(object2PackageName, data)
            }
            dialog.show(supportFragmentManager, "DIALOG")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(object2SignalHandler)
    }

    private fun launchAppWithData(packageName: String, data: IntArray) {
        packageManager.getLaunchIntentForPackage(packageName)?.apply {
            putExtra("DATA", data)
            startActivity(this)
        } ?: showToast("Програму $packageName не знайдено")
    }

    private fun launchAppWithSignal(packageName: String, signal: String) {
        packageManager.getLaunchIntentForPackage(packageName)?.apply {
            putExtra("SIGNAL", signal)
            startActivity(this)
        } ?: showToast("Програму $packageName не знайдено")
    }

    private fun showToast(text: String) {
        with(Toast(this)) {
            setText(text)
            duration = Toast.LENGTH_SHORT
            show()
        }
    }
}
