package com.example.creditscoreview

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.creditscoreview.view.AnimatedPathView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        startAnim()
//        lineAnim()
    }

    private fun lineAnim() {
        val view: AnimatedPathView =
            findViewById(R.id.animated_path)

        val observer: ViewTreeObserver = animated_path.getViewTreeObserver()
        observer?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this)
                val points = arrayOf(
                    floatArrayOf(0f, 0f),
                    floatArrayOf(view.width.toFloat(), 0f),
                    floatArrayOf(view.width.toFloat(), view.height.toFloat()),
                    floatArrayOf(0f, view.height.toFloat()),
                    floatArrayOf(0f, 0f),
                    floatArrayOf(view.width.toFloat(), view.height.toFloat()),
                    floatArrayOf(view.width.toFloat(), 0f),
                    floatArrayOf(0f, view.height.toFloat())
                )
                view.setPath(points)
            }
        })

        view.setOnClickListener(View.OnClickListener { view ->
            val anim =
                ObjectAnimator.ofFloat(view, "percentage", 0.0f, 1.0f)
            anim.duration = 2000
            anim.interpolator = LinearInterpolator()
            anim.start()
        })
    }

    private fun startAnim() {
        val observer: ViewTreeObserver = aet_2.getViewTreeObserver()
        observer?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                aet_2.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                /*val points = arrayOf(
                    floatArrayOf(0f, 0f),
                    floatArrayOf(aet_2.width.toFloat(), 0f),
                    floatArrayOf(aet_2.width.toFloat(), aet_2.height.toFloat()),
                    floatArrayOf(0f, aet_2.height.toFloat()),
                    floatArrayOf(0f, 0f),
                    floatArrayOf(aet_2.width.toFloat(), aet_2.height.toFloat()),
                    floatArrayOf(aet_2.width.toFloat(), 0f),
                    floatArrayOf(0f, aet_2.height.toFloat())
                )*/
//                aet_2.setPath()
//                aet_2.setPath(points)
            }
        })

//        aet_2.setPath()
//        aet_2.post { aet_2.setPath() }
        val anim =
            ObjectAnimator.ofFloat(aet_2, "percentage", 0f, 1.0f)
        anim.duration = 2000
        anim.interpolator = LinearInterpolator()
        anim.start()
    }
}
