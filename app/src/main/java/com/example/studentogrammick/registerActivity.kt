package com.example.studentogrammick

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class registerActivity : AppCompatActivity() {

    private lateinit var textView4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register) // Убедитесь, что это вызвано первым

        // Инициализация textView4 после setContentView
        textView4 = findViewById(R.id.textView4)

        // Настройка градиента для textView4
        setupGradientForTextView(textView4)

        // Обработка системных окон (insets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupGradientForTextView(textView: TextView) {
        val paint = textView.paint
        val height = textView.textSize // Высота текста

        // Установка вертикального градиента
        val gradient = LinearGradient(
            0f, 0f, 0f, height, // Вертикальный градиент
            intArrayOf(
                Color.parseColor("#00A3FF"),
                Color.parseColor("#7000FF")
            ),
            null,
            Shader.TileMode.CLAMP
        )
        textView.paint.shader = gradient
    }
}

