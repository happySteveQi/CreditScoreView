package com.example.creditscoreview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.example.creditscoreview.R

class AnimationEditText : EditText {
    companion object {
        val DEBUG: Boolean = true
        val TAG: String = "AnimationEditText"
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var BOTTOM_SPACE = dp2px(15f)

    private var mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mBottomLineColor = resources.getColor(R.color.color_FF98A22C, null)
    private var mProgress = 0f
    private var mPathLength = 0f
    private var mPath = Path()
    val anim =
        ObjectAnimator.ofFloat(this, "percentage", 0f, 1.0f)

    init {
        mLinePaint.color = mBottomLineColor
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.strokeWidth = dp2px(1f).toFloat()

        anim.duration = 100
        anim.interpolator = LinearInterpolator()


    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            startLineAnim()
            if (DEBUG) Log.d(TAG, "onFocusChanged $focused direction $direction")
        } else {
            resetLineAnim()
        }

    }

    private fun resetLineAnim() {
        anim.reverse()
        invalidate()
    }


    private fun startLineAnim() {

        post {
            BOTTOM_SPACE = ((height - paint.textSize) / 2 - dp2px(2f)).toInt()
            setPath()
            anim.start()
        }
    }


    private fun setPath() {
        mPath.moveTo(0f + marginLeft + paddingLeft, height.toFloat() - paddingBottom - BOTTOM_SPACE)
        mPath.lineTo(
            width.toFloat() - marginRight - paddingRight,
            height.toFloat() - paddingBottom - BOTTOM_SPACE
        )

        var measure = PathMeasure(mPath, false)
        mPathLength = measure.length

    }

    fun setPercentage(percentage: Float) {
        if (DEBUG) Log.d(TAG, "--- setPercentage --- ")
        if (percentage < 0.0f || percentage > 1.0f) {
            throw IllegalArgumentException("The percentage must be between 0.0 to 1.0")
        }
        mProgress = percentage
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (DEBUG) Log.d(TAG, "--- onDraw --- $mProgress")
        val pathEffect: PathEffect = DashPathEffect(
            floatArrayOf(mPathLength, mPathLength),
            mPathLength - mPathLength * mProgress
        )
        mLinePaint.pathEffect = pathEffect

        canvas!!.drawPath(mPath, mLinePaint)

    }
}