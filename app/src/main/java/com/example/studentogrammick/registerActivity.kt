package com.example.studentogrammick

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class registerActivity : AppCompatActivity() {

    private lateinit var textView4: TextView // Текст "Регистрация"
    private lateinit var animatedView: View // Анимируемый элемент
    private lateinit var startAnimationButton: Button // Кнопка для запуска анимации

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Инициализация элементов
        textView4 = findViewById(R.id.textView4) // Текст "Регистрация"
        setupGradientForTextView(textView4) // Настройка градиента для текста

        // Обработка системных окон (insets) для корневого контейнера
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация анимируемого элемента и кнопки
        animatedView = findViewById(R.id.animatedView) // Анимируемый элемент
        startAnimationButton = findViewById(R.id.starttAnimationButton) // Кнопка "Показать форму"

        // Добавляем слушатель нажатия кнопки
        startAnimationButton.setOnClickListener {
            println("Button clicked!") // Лог для проверки
            animateView(animatedView) // Анимируем animatedView
        }
    }

    /**
     * Настройка вертикального градиента для TextView
     */
    private fun setupGradientForTextView(textView: TextView) {
        val paint = textView.paint
        val height = textView.textSize // Высота текста
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

    /**
     * Анимация появления View с использованием ObjectAnimator
     */
    private fun animateView(view: View) {
        val animator = ObjectAnimator.ofFloat(
            view,
            "translationX", // Свойство для анимации
            -view.width.toFloat(), // Начальная позиция (за пределами экрана)
            0f // Конечная позиция (на экране)
        )
        animator.duration = 1000 // Длительность анимации
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                println("Animation started!")
            }

            override fun onAnimationEnd(animation: Animator) {
                println("Animation ended!")
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.start()
    }
}