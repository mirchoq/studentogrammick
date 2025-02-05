package com.example.studentogrammick

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.CountDownTimer
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textLoad: TextView
    private lateinit var textView2: TextView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var imageView2: ImageView // Добавляем imageView2
    private lateinit var gestureDetector: GestureDetector // Для обработки двойного нажатия
    private val loadingMessages = listOf(
        "Грузим.....",
        "Грузим....",
        "Грузим...",
        "Грузим..",
        "Грузим."
    )
    // Список для хранения аниматоров
    private val animators = mutableListOf<ObjectAnimator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Инициализация textView после setContentView
        textLoad = findViewById(R.id.textload)
        textView2 = findViewById(R.id.textView2)
        imageView2 = findViewById(R.id.imageView2) // Инициализация imageView2

        // Настройка градиента для textView2
        val paint = textView2.paint
        val width = paint.measureText(textView2.text.toString())
        val height = textView2.textSize // Высота текста

        val gradient = LinearGradient(
            0f, 1f, 1f, height, // Вертикальный градиент
            intArrayOf(
                Color.parseColor("#00A3FF"),
                Color.parseColor("#522BFF"),
                Color.parseColor("#7000FF")
            ),
            null,
            Shader.TileMode.CLAMP
        )
        textView2.paint.shader = gradient

        // Обработка системных окон (insets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация GestureDetector для обработки двойного нажатия
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                // Переход на registerActivity при двойном нажатии
                startActivity(Intent(this@MainActivity, registerActivity::class.java))
                return true
            }
        })

        // Установка обработчика касаний для imageView2
        imageView2.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event) // Передача события в GestureDetector
            true
        }

        // Инициализация анимаций
        setupRotations()

        // Запуск таймера
        startCountDownTimer()
    }

    private fun setupRotations() {
        // Настройка анимации для imageView8 (по часовой)
        val iv8 = findViewById<ImageView>(R.id.imageView8)
        animators.add(createRotationAnimator(iv8, clockwise = true, duration = 2000))

        // Настройка анимации для imageView9 (против часовой)
        val iv9 = findViewById<ImageView>(R.id.imageView9)
        animators.add(createRotationAnimator(iv9, clockwise = false, duration = 2200))

        // Настройка анимации для imageView10 (по часовой)
        val iv10 = findViewById<ImageView>(R.id.imageView10)
        animators.add(createRotationAnimator(iv10, clockwise = true, duration = 2400))

        // Запуск всех анимаций
        animators.forEach { it.start() }
    }

    private fun createRotationAnimator(
        view: ImageView,
        clockwise: Boolean,
        duration: Long
    ): ObjectAnimator {
        val from = if (clockwise) 0f else 360f
        val to = if (clockwise) 360f else 0f

        return ObjectAnimator.ofFloat(view, "rotation", from, to).apply {
            this.duration = duration
            repeatCount = ObjectAnimator.INFINITE
            interpolator = android.view.animation.LinearInterpolator()
        }
    }

    private fun startCountDownTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateText((millisUntilFinished / 1000).toInt())
            }

            override fun onFinish() {
                stopAnimations()
                startActivity(Intent(this@MainActivity, registerActivity::class.java))
                finish()
            }
        }.start()
    }

    private fun updateText(secondsPassed: Int) {
        val messageIndex = secondsPassed % loadingMessages.size
        textLoad.text = loadingMessages[messageIndex]
    }

    private fun stopAnimations() {
        animators.forEach { it.cancel() }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
        stopAnimations()
    }
}