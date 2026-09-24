package com.example.project1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val curNum = findViewById<EditText>(R.id.EditTextCur)
        val backNum = findViewById<TextView>(R.id.textViewNum)
        val operationText = findViewById<TextView>(R.id.textViewSign)

        var num1 = 0.0
        var operation = ""

        val digitButtons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        )

        for (id in digitButtons) {
            findViewById<Button>(id).setOnClickListener {
                val digit = (it as Button).text.toString()
                curNum.append(digit)
            }
        }

        val btnDot: Button = findViewById(R.id.buttonDot)
        btnDot.setOnClickListener {
            if (curNum.text.isEmpty()) {
                curNum.append("0.")
            } else {
                curNum.append(".")
            }
        }

        fun handleOperation(op: String) {
            if (curNum.text.isEmpty()) {
                curNum.error = "Введите число"
                curNum.requestFocus()
                Handler(Looper.getMainLooper()).postDelayed({
                    curNum.error = null
                }, 1500)
                return
            }

            if (backNum.text.isEmpty() && operationText.text.isEmpty()) {
                operation = op
                operationText.text = operation
                backNum.text = curNum.text
                curNum.text.clear()
                num1 = backNum.text.toString().toDouble()
            }
            else if (backNum.text.isNotEmpty() && operationText.text.isNotEmpty() && curNum.text.isNotEmpty()) {
                val num2 = curNum.text.toString().toDouble()
                var result = 0.0
                when (operation) {
                    "+" -> result = num1 + num2
                    "-" -> result = num1 - num2
                    "*" -> result = num1 * num2
                    "/" -> result = num1 / num2
                }
                if (result % 1.0 == 0.0) {
                    backNum.text = result.roundToInt().toString()
                } else {
                    backNum.text = result.toString()
                }
                num1 = result
                curNum.text.clear()
                operation = op
                operationText.text = operation
            }
            else {
                operation = op
                operationText.text = operation
            }
        }

        val btnAdd: Button = findViewById(R.id.buttonPlus)
        btnAdd.setOnClickListener { handleOperation("+") }

        val btnMin: Button = findViewById(R.id.buttonMinus)
        btnMin.setOnClickListener { handleOperation("-") }

        val btnDiv: Button = findViewById(R.id.buttonDiv)
        btnDiv.setOnClickListener { handleOperation("/") }

        val btnMulti: Button = findViewById(R.id.buttonMult)
        btnMulti.setOnClickListener { handleOperation("*") }

        val btnEq: Button = findViewById(R.id.buttonEquals)
        btnEq.setOnClickListener {
            try {
                val num2 = curNum.text.toString().toDouble()
                var result = 0.0

                if (operation == "/" && num2 == 0.0) {
                    curNum.error = "Деление на ноль не допускается"
                    curNum.requestFocus()
                    Handler(Looper.getMainLooper()).postDelayed({
                        curNum.error = null
                    }, 1500)
                    return@setOnClickListener
                }

                when (operation) {
                    "+" -> result = num1 + num2
                    "-" -> result = num1 - num2
                    "*" -> result = num1 * num2
                    "/" -> result = num1 / num2
                }

                if (result % 1.0 == 0.0) {
                    curNum.setText(result.roundToInt().toString())
                } else {
                    curNum.setText(result.toString())
                }

                backNum.text = ""
                operationText.text = ""

            } catch (e: Exception) {
                if (curNum.text.isEmpty()) {
                    curNum.error = "Введите число"
                    curNum.requestFocus()
                    Handler(Looper.getMainLooper()).postDelayed({
                        curNum.error = null
                    }, 1500)
                } else if (backNum.text.isEmpty()) {
                    curNum.error = "Введите операцию"
                    curNum.requestFocus()
                    Handler(Looper.getMainLooper()).postDelayed({
                        curNum.error = null
                    }, 1500)
                }
            }
        }
    }
}
