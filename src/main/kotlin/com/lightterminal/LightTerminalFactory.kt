package com.lightterminal

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class LightTerminalFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentManager = toolWindow.contentManager
        val disposable = Disposer.newDisposable("CCTerminal-${System.nanoTime()}")

        val panel = LightTerminalPanel(project, disposable)

        val content = contentManager.factory.createContent(panel, "CC Terminal", false)
        content.setDisposer(disposable)
        contentManager.addContent(content)
    }
}
