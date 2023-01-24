package com.arbelkilani.binge.tv.common.ui.dot

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout

class TypingMessageDotsView(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {

    companion object {
        const val DEFAULT_CIRCLE_DURATION = 1000L
        const val DEFAULT_START_ANIM_CIRCLE_DURATION = 300L
        const val DEFAULT_MAX_ALPHA = 1f
        const val DEFAULT_MIN_ALPHA = .5f
        const val DEFAULT_DOTS_MARGIN = 5
        const val DEFAULT_DOTS_COUNT = 3
    }

    private val circles = mutableListOf<View>()

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        //setCircles()
    }

    /**

    private fun setCircles() {
        circles.clear()
        removeAllViews()
        for (i in 0 until DEFAULT_DOTS_COUNT) {
            val view = obtainCircle(R.drawable.ic_typing_dot)
            addView(view)
            circles.add(view)
        }
    }

    private fun obtainCircle(@DrawableRes imageCircle: Int): View {
        val image = AppCompatImageView(context)
        image.id = View.generateViewId()
        val params = MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(DEFAULT_DOTS_MARGIN)
        image.layoutParams = params
        image.setImageResource(imageCircle)
        image.adjustViewBounds = false
        return image
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        circles.forEachIndexed { index, circle -> animateCircle(index, circle) }
    }

    private fun animateCircle(index: Int, circle: View) {
        ObjectAnimator.ofFloat(circle, "alpha", DEFAULT_MAX_ALPHA, DEFAULT_MIN_ALPHA).apply {
            duration = DEFAULT_CIRCLE_DURATION
            startDelay = DEFAULT_START_ANIM_CIRCLE_DURATION * index
            repeatCount = ValueAnimator.INFINITE
        }.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        circles.forEach { it.clearAnimation() }
    }

    **/
}