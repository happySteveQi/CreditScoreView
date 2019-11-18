package com.example.creditscoreview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.creditscoreview.BuildConfig.DEBUG
import com.example.creditscoreview.R

class CreditLineView : View {
    companion object {
        val TAG = "CreditLineView"
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val colorArrays = arrayOf(
        R.color.color_FFDD2335, R.color.color_FFE54933, R.color.color_FFF59130
        , R.color.color_FFFDB62E, R.color.color_FFCAAC2D, R.color.color_FF98A22C
        , R.color.color_FF65982B
    )

    private val mPaint = Paint()

    private var SCALED_WIDTH: Float = 0f
    private var NORMAL_HEIGHT: Float = 0f
    private var EACH_LENGTH: Float = 0f
    private val GAP_LENGTH = dip2px(1f)

    private var mIndex = 5

    init {

        mPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.BUTT
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        SCALED_WIDTH = w.toFloat()
        EACH_LENGTH = (SCALED_WIDTH - (GAP_LENGTH * (colorArrays.size - 1))).div(colorArrays.size)
        NORMAL_HEIGHT = h.toFloat()
        mPaint.strokeWidth = NORMAL_HEIGHT

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawLines(canvas)
    }

    fun refresh(index: Int) {
        if (DEBUG) Log.d(TAG, " currentIndex = $index")
        mIndex = index
        invalidate()
    }

    private fun drawLines(canvas: Canvas?) {
        for (i in colorArrays.indices) {
            if (DEBUG) Log.d(TAG, "EACH_LENGTH = $EACH_LENGTH ------ i = $i")
            mPaint.color = resources.getColor(colorArrays[i])
            var normalY: Float = NORMAL_HEIGHT / 2
            var circleRadius: Float = NORMAL_HEIGHT / 4
            when (i) {
                0 -> {
                    if (mIndex == 0) {
                        mPaint.strokeWidth = NORMAL_HEIGHT
                        circleRadius = NORMAL_HEIGHT / 2
                        canvas?.apply {
                            drawArc(
                                0f,
                                0f,
                                NORMAL_HEIGHT,
                                NORMAL_HEIGHT,
                                -270f,
                                180f,
                                false,
                                mPaint
                            )
                            drawLine(NORMAL_HEIGHT / 2, normalY, EACH_LENGTH, normalY, mPaint)
                        }

                    } else {// 当前选中的非第一个
                        mPaint.strokeWidth = NORMAL_HEIGHT / 2
                        canvas?.drawCircle(
                            circleRadius,
                            normalY,
                            circleRadius,
                            mPaint
                        )
                        canvas?.drawLine(NORMAL_HEIGHT / 4, normalY, EACH_LENGTH, normalY, mPaint)
                    }

                }
                colorArrays.size - 1 -> {
                    if (mIndex == colorArrays.size - 1) {
                        mPaint.strokeWidth = NORMAL_HEIGHT

                        canvas?.apply {
                            drawArc(
                                SCALED_WIDTH - NORMAL_HEIGHT,
                                0f,
                                SCALED_WIDTH,
                                NORMAL_HEIGHT,
                                -90f,
                                180f,
                                false,
                                mPaint
                            )
                            drawLine(
                                (EACH_LENGTH * i) + GAP_LENGTH * i,
                                normalY,
                                EACH_LENGTH * (i + 1) + GAP_LENGTH * i - NORMAL_HEIGHT / 2,
                                normalY,
                                mPaint
                            )
                        }
                    } else {// 当前选中的非最后一个
                        mPaint.strokeWidth = NORMAL_HEIGHT / 2
                        canvas?.apply {
                            drawLine(
                                (EACH_LENGTH * i) + GAP_LENGTH * i,
                                normalY,
                                EACH_LENGTH * (i + 1) + GAP_LENGTH * i - NORMAL_HEIGHT / 4,
                                normalY,
                                mPaint
                            )
                            drawCircle(
                                SCALED_WIDTH - circleRadius,
                                normalY,
                                circleRadius,
                                mPaint
                            )
                        }

                    }


                }
                else -> {
                    if (mIndex == i) {

                        mPaint.strokeWidth = NORMAL_HEIGHT
                        canvas?.drawLine(
                            (EACH_LENGTH * i) + GAP_LENGTH * i,
                            normalY,
                            EACH_LENGTH * (i + 1) + GAP_LENGTH * i,
                            normalY,
                            mPaint
                        )
                    } else {
                        mPaint.strokeWidth = NORMAL_HEIGHT / 2
                        canvas?.drawLine(
                            (EACH_LENGTH * i) + GAP_LENGTH * i,
                            normalY,
                            EACH_LENGTH * (i + 1) + GAP_LENGTH * i,
                            normalY,
                            mPaint
                        )
                    }

                }
            }
        }

    }

}