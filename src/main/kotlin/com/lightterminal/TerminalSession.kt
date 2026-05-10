package com.lightterminal

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.terminal.JBTerminalWidget
import org.jetbrains.plugins.terminal.ShellTerminalWidget
import org.jetbrains.plugins.terminal.TerminalView
import java.io.IOException

class TerminalSession(
    private val project: Project,
    private val parentDisposable: Disposable
) : Disposable {

    private var shellWidget: ShellTerminalWidget? = null

    val terminalWidget: JBTerminalWidget?
        get() = shellWidget

    fun start(command: String): JBTerminalWidget {
        stop()

        val terminalView = project.getService(TerminalView::class.java)
        val widget = terminalView.createLocalShellWidget(
            project.basePath ?: System.getProperty("user.home"),
            "CC Terminal"
        )

        Disposer.register(parentDisposable, widget)

        shellWidget = widget

        try {
            widget.executeCommand(command)
        } catch (_: IOException) {
        }

        return widget
    }

    fun sendText(text: String) {
        val widget = shellWidget ?: return
        try {
            widget.terminalStarter?.sendString(text + "\r", true)
        } catch (_: Exception) {
        }
    }

    fun stop() {
        shellWidget = null
    }

    override fun dispose() {
        stop()
    }
}
