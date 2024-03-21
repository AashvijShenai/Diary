package com.example.diaryv2

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


data class Dot(val view: View, var x: Float, var y: Float)

class MainActivity : AppCompatActivity() {
    private val dots = mutableListOf<Dot>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val dot1 = findViewById<View>(R.id.dot1)
        val dot2 = findViewById<View>(R.id.dot2)
        val dot3 = findViewById<View>(R.id.dot3)
        val dot4 = findViewById<View>(R.id.dot4)
        val dot5 = findViewById<View>(R.id.dot5)
        val dot6 = findViewById<View>(R.id.dot6)
        val dot7 = findViewById<View>(R.id.dot7)
        val dot8 = findViewById<View>(R.id.dot8)
        val dot9 = findViewById<View>(R.id.dot9)
        val dot10 = findViewById<View>(R.id.dot10)
        val dot11 = findViewById<View>(R.id.dot11)
        val dot12 = findViewById<View>(R.id.dot12)
        val dot13 = findViewById<View>(R.id.dot13)
        val dot14 = findViewById<View>(R.id.dot14)
        val dot15 = findViewById<View>(R.id.dot15)
        val dot16 = findViewById<View>(R.id.dot16)
        val dot17 = findViewById<View>(R.id.dot17)
        val dot18 = findViewById<View>(R.id.dot18)
        val dot19 = findViewById<View>(R.id.dot19)
        val dot20 = findViewById<View>(R.id.dot20)
        val dot21 = findViewById<View>(R.id.dot21)
        val dot22 = findViewById<View>(R.id.dot22)
        val dot23 = findViewById<View>(R.id.dot23)
        val dot24 = findViewById<View>(R.id.dot24)
        val dot25 = findViewById<View>(R.id.dot25)
        val dot26 = findViewById<View>(R.id.dot26)
        val dot27 = findViewById<View>(R.id.dot27)
        val dot28 = findViewById<View>(R.id.dot28)
        val dot29 = findViewById<View>(R.id.dot29)
        val dot30 = findViewById<View>(R.id.dot30)
        val dot31 = findViewById<View>(R.id.dot31)
        val dot32 = findViewById<View>(R.id.dot32)
        val dot33 = findViewById<View>(R.id.dot33)
        val dot34 = findViewById<View>(R.id.dot34)
        val dot35 = findViewById<View>(R.id.dot35)
        val dot36 = findViewById<View>(R.id.dot36)


        dots.add(Dot(dot1, dot1.x, dot1.y))
        dots.add(Dot(dot2, dot2.x, dot2.y))
        dots.add(Dot(dot3, dot3.x, dot3.y))
        dots.add(Dot(dot4, dot4.x, dot4.y))
        dots.add(Dot(dot5, dot5.x, dot5.y))
        dots.add(Dot(dot6, dot6.x, dot6.y))
        dots.add(Dot(dot7, dot7.x, dot7.y))
        dots.add(Dot(dot8, dot8.x, dot8.y))
        dots.add(Dot(dot9, dot9.x, dot9.y))
        dots.add(Dot(dot10, dot10.x, dot10.y))
        dots.add(Dot(dot11, dot11.x, dot11.y))
        dots.add(Dot(dot12, dot12.x, dot12.y))
        dots.add(Dot(dot13, dot13.x, dot13.y))
        dots.add(Dot(dot14, dot14.x, dot14.y))
        dots.add(Dot(dot15, dot15.x, dot15.y))
        dots.add(Dot(dot16, dot16.x, dot16.y))
        dots.add(Dot(dot17, dot17.x, dot17.y))
        dots.add(Dot(dot18, dot18.x, dot18.y))
        dots.add(Dot(dot19, dot19.x, dot19.y))
        dots.add(Dot(dot20, dot20.x, dot20.y))
        dots.add(Dot(dot21, dot21.x, dot21.y))
        dots.add(Dot(dot22, dot22.x, dot22.y))
        dots.add(Dot(dot23, dot23.x, dot23.y))
        dots.add(Dot(dot24, dot24.x, dot24.y))
        dots.add(Dot(dot25, dot25.x, dot25.y))
        dots.add(Dot(dot26, dot26.x, dot26.y))
        dots.add(Dot(dot27, dot27.x, dot27.y))
        dots.add(Dot(dot28, dot28.x, dot28.y))
        dots.add(Dot(dot29, dot29.x, dot29.y))
        dots.add(Dot(dot30, dot30.x, dot30.y))
        dots.add(Dot(dot31, dot31.x, dot31.y))
        dots.add(Dot(dot32, dot32.x, dot32.y))
        dots.add(Dot(dot33, dot33.x, dot33.y))
        dots.add(Dot(dot34, dot34.x, dot34.y))
        dots.add(Dot(dot35, dot35.x, dot35.y))
        dots.add(Dot(dot36, dot36.x, dot36.y))

        animateDots()


        // Define animators for other dots and lines similarly
        val goldenButton = findViewById<View>(R.id.goldenButton)
        goldenButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun animateDots() {
        dots.forEach { dot ->
            val randomDuration = Random.nextLong(500, 1500) // Random duration between 0.5 and 1.5 seconds
            val randomRangeX = Random.nextInt(5, 250) // Random movement range for X axis
            val randomRangeY = Random.nextInt(5, 350) // Random movement range for Y axis

            val animatorX = ObjectAnimator.ofFloat(dot.view, View.TRANSLATION_X, -randomRangeX.toFloat(), randomRangeX.toFloat()).apply {
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                duration = randomDuration
                interpolator = AccelerateDecelerateInterpolator()
            }

            val animatorY = ObjectAnimator.ofFloat(dot.view, View.TRANSLATION_Y, -randomRangeY.toFloat(), randomRangeY.toFloat()).apply {
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                duration = randomDuration
                interpolator = AccelerateDecelerateInterpolator()
            }

            animatorX.start()
            animatorY.start()
        }

        // Periodically update dot positions
        updateDotPositions()
    }

    private fun updateDotPositions() {
        dots.forEach { dot ->
            val randomX = Random.nextInt(-100, 110)
            val randomY = Random.nextInt(-100, 110)
            dot.x += randomX
            dot.y += randomY
            dot.view.x = dot.x
            dot.view.y = dot.y
        }

        // Call the method again after a delay to continue the animation
        Handler(Looper.getMainLooper()).postDelayed({
            updateDotPositions()
        }, 1000) // Repeat every second (1000 milliseconds)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}