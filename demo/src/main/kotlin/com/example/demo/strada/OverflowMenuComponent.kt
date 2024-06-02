package com.example.demo.strada

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.base.NavDestination
import com.example.demo.databinding.MenuComponentHamburgerBinding
import dev.hotwire.strada.BridgeComponent
import dev.hotwire.strada.BridgeDelegate
import dev.hotwire.strada.Message
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
    val menu = toolbar?.menu ?: return
    val inflater = LayoutInflater.from(fragment.requireContext())
    val binding = MenuComponentHamburgerBinding.inflate(inflater)

    binding.formSubmit.apply {
      setOnClickListener {
//        performClick()
      }
    }
    menu.removeItem(0)
    menu.add(0, 0, 0, "").apply {
      actionView = binding.root
      setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
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
