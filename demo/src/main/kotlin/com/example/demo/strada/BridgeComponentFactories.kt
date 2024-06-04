package com.example.demo.strada

import dev.hotwire.strada.BridgeComponentFactory

val bridgeComponentFactories = listOf(
    BridgeComponentFactory("menu", ::MenuComponent),
    BridgeComponentFactory("overflow-menu", ::OverflowMenuComponent)
)
