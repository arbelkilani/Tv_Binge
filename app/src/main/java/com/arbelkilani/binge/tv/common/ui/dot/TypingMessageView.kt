package com.arbelkilani.binge.tv.common.ui.dot

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypingMessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    /**

    val views: TypingMessageLayoutBinding

    @Inject
    lateinit var typingHelper: TypingHelper

    init {
        inflate(context, R.layout.typing_message_layout, this)
        views = TypingMessageLayoutBinding.bind(this)
    }

    fun render(typingUsers: List<SenderInfo>, avatarRenderer: AvatarRenderer) {
        views.usersName.text = typingHelper.getNotificationTypingMessage(typingUsers)
        views.avatars.render(typingUsers, avatarRenderer)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeAllViews()
    }

    **/
}