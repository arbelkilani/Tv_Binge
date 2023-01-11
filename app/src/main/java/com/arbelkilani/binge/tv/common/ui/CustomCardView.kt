package com.arbelkilani.binge.tv.common.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.material.card.MaterialCardView

class CustomCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    companion object {
        const val MIN_SCALE = 0.95f
        const val DEFAULT_SCALE = 1f
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val x = event!!.x
        val y = event.y
        if (rectF.contains(x, y) &&
            (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE)
        ) {
            scaleX = MIN_SCALE
            scaleY = MIN_SCALE
        } else {
            scaleX = DEFAULT_SCALE
            scaleY = DEFAULT_SCALE
        }

        return super.onTouchEvent(event)
    }
}