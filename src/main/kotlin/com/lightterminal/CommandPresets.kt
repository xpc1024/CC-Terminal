package com.lightterminal

object CommandPresets {
    data class Preset(val label: String, val command: String)

    val ALL = listOf(
        Preset("claude --dangerously-skip-permissions", "claude --dangerously-skip-permissions"),
        Preset("claude", "claude")
    )

    val DEFAULT = ALL[0]
}
