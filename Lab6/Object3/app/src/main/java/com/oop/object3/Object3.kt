package com.oop.object3

import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.abs
import java.lang.Math.round

class Object3 : AppCompatActivity() {
    private lateinit var determinantTextView: TextView

    private var wasNewIntent = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        determinantTextView = findViewById(R.id.determinant)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        wasNewIntent = true
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && wasNewIntent) {
            if (intent.getStringExtra("SIGNAL") == "START") {
                val data = readFromClipboard()
                if (data != "") {
                    val matrix = deserializeMatrix(data)
                    val determinant = calculateDeterminant(matrix)
                    determinantTextView.text = determinant.toString()
                } else {
                    with(Toast(this)) {
                        setText("Виникла помилка читання з буфера обміну")
                        duration = Toast.LENGTH_LONG
                        show()
                    }
                }
            }
            wasNewIntent = false
        }
    }

    private fun deserializeMatrix(str: String): Array<IntArray> {
        val rows = str.split("\n")
        val n = rows.size
        val matrix = Array(n) { IntArray(n) }
        rows.forEachIndexed { i, row ->
            val rowFields = row.split("\t")
            rowFields.forEachIndexed { j, value ->
                matrix[i][j] = value.toInt()
            }
        }
        return matrix
    }

    private fun calculateDeterminant(matrix: Array<IntArray>): Int {
        val n = matrix.size
        if (n == 1) return matrix[0][0]
        val matrixDouble = Array(n) { i ->
            DoubleArray(n) { j ->
                matrix[i][j].toDouble()
            }
        }
        for (i in 0 until n - 1) {
            var maxRow = i
            for (k in i + 1 until n) {
                if (abs(matrixDouble[k][i]) > abs(matrixDouble[maxRow][i])) {
                    maxRow = k
                }
            }
            if (maxRow != i) {
                val temp = matrixDouble[maxRow]
                matrixDouble[maxRow] = matrixDouble[i]
                matrixDouble[i] = temp
            }
            if (matrixDouble[i][i] == 0.0) return 0

            for (j in i + 1 until n) {
                val factor = matrixDouble[j][i] / matrixDouble[i][i]
                for (k in i + 1 until n) {
                    matrixDouble[j][k] -= factor * matrixDouble[i][k]
                }
            }
        }
        var determinant = 1.0
        for (i in 0 until n) {
            determinant *= matrixDouble[i][i]
        }
        return round(determinant).toInt()
    }

    private fun readFromClipboard(): String {
        val manager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = manager.primaryClip
        return clipData?.let { data ->
           data.getItemAt(0)?.let { item ->
                item.text.toString()
            } ?: ""
        } ?: ""
    }
}
