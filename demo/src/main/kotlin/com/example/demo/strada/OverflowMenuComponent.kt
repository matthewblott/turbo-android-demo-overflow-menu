package com.example.demo.strada

import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import dev.hotwire.strada.BridgeComponent
import dev.hotwire.strada.BridgeDelegate
import dev.hotwire.strada.Message
import com.example.demo.R
import com.example.demo.base.NavDestination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class OverflowMenuComponent(
    name: String,
    private val delegate: BridgeDelegate<NavDestination>
) : BridgeComponent<NavDestination>(name, delegate) {

    private val fragment: Fragment
        get() = delegate.destination.fragment
    private val toolbar: Toolbar?
        get() = fragment.view?.findViewById(R.id.toolbar)

    override fun onReceive(message: Message) {
        when (message.event) {
            "connect" -> handleConnectEvent(message)
            else -> Log.w("TurboDemo", "Unknown event for message: $message")
        }
    }

    private fun handleConnectEvent(message: Message) {
        val data = message.data<MessageData>() ?: return
        showOverflowMenuItem(data)
    }

    private fun showOverflowMenuItem(data: MessageData) {
        val toolbar = toolbar ?: return

        toolbar.menu.findItem(R.id.overflow)?.apply {
            isVisible = true
            title = data.label
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.overflow -> {
                    performClick()
                    true
                }
                else -> false
            }
        }
    }

    private fun performClick() {
        replyTo("connect")
    }

    @Serializable
    data class MessageData(
        @SerialName("label") val label: String
    )
}
