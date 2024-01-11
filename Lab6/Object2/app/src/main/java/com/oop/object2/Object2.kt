package com.oop.object2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class Object2 : AppCompatActivity() {
    private lateinit var matrixTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        matrixTextView = findViewById(R.id.matrix)
        handleIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent()
    }

    private fun handleIntent() {
        val data = intent.getIntArrayExtra("DATA")
        data?.let {
            val (n, min, max) = it
            var taskEndingStatus = 0
            try {
                val matrix = generateMatrix(n, min, max)
                showMatrix(matrix)
                val strMatrix = serializeMatrix(matrix)
                writeToClipboard(strMatrix)
            } catch (e: Exception) {
                taskEndingStatus = 1
            }
            sendTaskEndingSignal(taskEndingStatus)
        }
    }

    private fun generateMatrix(n: Int, min: Int, max: Int): Array<IntArray> {
        return Array(n) {
            IntArray(n) {
                (min..max).random()
            }
        }
    }

    private fun showMatrix(matrix: Array<IntArray>) {
        val str = StringBuilder()
        val n = matrix.size
        (0 until n).forEach { i ->
            (0 until n - 1).forEach { j ->
                val value = matrix[i][j]
                val prefix = if (value >= 0) " " else ""
                val spaces = when (abs(value)) {
                    in 0..9 -> "      "
                    in 10..99 -> "    "
                    in 100..999 -> "  "
                    else -> " "
                }
                str.append("$prefix$value$spaces")
            }
            str.append("${matrix[i][n - 1]}\n\n")
        }
        matrixTextView.text = str.dropLast(2).toString()
    }

    private fun serializeMatrix(matrix: Array<IntArray>): String {
        val str = StringBuilder()
        val n = matrix.size
        (0 until n).forEach { i ->
            (0 until n - 1).forEach { j ->
                str.append("${matrix[i][j]}\t")
            }
            str.append("${matrix[i][n - 1]}\n")
        }
        return str.dropLast(1).toString()
    }

    private fun writeToClipboard(data: String) {
        val manager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("MATRIX", data)
        manager.setPrimaryClip(clipData)
    }

    private fun sendTaskEndingSignal(status: Int) {
        Intent("OBJECT2_SEND_SIGNAL").apply {
            putExtra("SIGNAL",
                if (status == 0) "TASK_END_SUCCESS"
                else "TASK_END_FAILURE"
            )
            sendBroadcast(this)
        }
    }
}
