package com.arbelkilani.binge.tv.common.ui.dot

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class TypingMessageAvatar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val AVATAR_SIZE_DP = 20
        const val OVERLAP_FACT0R = -3 // =~ 30% to left
    }

    /**

    fun render(typingUsers: List<SenderInfo>, avatarRenderer: AvatarRenderer) {
        removeAllViews()
        for ((index, value) in typingUsers.withIndex()) {
            val avatar = ImageView(context)
            avatar.id = View.generateViewId()
            val layoutParams = MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            if (index != 0) layoutParams.marginStart = DimensionConverter(resources).dpToPx(AVATAR_SIZE_DP / OVERLAP_FACT0R)
            layoutParams.width = DimensionConverter(resources).dpToPx(AVATAR_SIZE_DP)
            layoutParams.height = DimensionConverter(resources).dpToPx(AVATAR_SIZE_DP)
            avatar.layoutParams = layoutParams
            avatarRenderer.render(value.toMatrixItem(), avatar)
            addView(avatar)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeAllViews()
    }

    **/
}