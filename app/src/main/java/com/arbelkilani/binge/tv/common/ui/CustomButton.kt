package com.arbelkilani.binge.tv.common.ui

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton


class CustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    companion object {
        private const val CLICK_OPACITY = .7f
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val x = event.x
        val y = event.y
        alpha = if (rectF.contains(x, y) && (event.action == MotionEvent.ACTION_DOWN
                    || event.action == MotionEvent.ACTION_MOVE) && isEnabled
        ) {
            CLICK_OPACITY
        } else {
            1f
        }
        return super.onTouchEvent(event)
    }
}