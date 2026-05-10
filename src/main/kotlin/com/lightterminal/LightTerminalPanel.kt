package com.lightterminal

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.terminal.JBTerminalWidget
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.*

class LightTerminalPanel(
    private val project: Project,
    parentDisposable: Disposable
) : JPanel(BorderLayout()), Disposable {

    private val session = TerminalSession(project, this)
    private var isMultiLineMode = true

    private val commandCombo = ComboBox(CommandPresets.ALL.map { it.label }.toTypedArray())
    private val toggleModeButton = JButton("Multi-line: ON")
    private val runButton = JButton("Run")

    private val inputPanel = MultiLineInputPanel { text -> sendToProcess(text) }
    private var terminalWidget: JBTerminalWidget? = null
    private val terminalContainer = JPanel(BorderLayout())

    init {
        Disposer.register(parentDisposable, this)

        val toolbar = JPanel(FlowLayout(FlowLayout.LEFT))
        toolbar.add(JLabel("Command:"))
        commandCombo.selectedItem = CommandPresets.DEFAULT.label
        toolbar.add(commandCombo)
        toolbar.add(runButton)
        toolbar.add(Box.createHorizontalStrut(10))
        toolbar.add(toggleModeButton)
        add(toolbar, BorderLayout.NORTH)

        val centerPanel = JPanel(BorderLayout())
        centerPanel.add(inputPanel, BorderLayout.NORTH)
        centerPanel.add(terminalContainer, BorderLayout.CENTER)
        add(centerPanel, BorderLayout.CENTER)

        runButton.addActionListener { startSession() }
        toggleModeButton.addActionListener { toggleMode() }

        registerKeyboardShortcut()
    }

    private fun startSession() {
        val selectedItem = commandCombo.selectedItem as? String ?: CommandPresets.DEFAULT.label
        val preset = CommandPresets.ALL.find { it.label == selectedItem }
        val command = preset?.command ?: selectedItem

        terminalContainer.removeAll()

        val widget = session.start(command)
        terminalWidget = widget

        terminalContainer.add(widget, BorderLayout.CENTER)
        terminalContainer.revalidate()
        terminalContainer.repaint()

        // Force focus back to our plugin tool window
        SwingUtilities.invokeLater {
            ToolWindowManager.getInstance(project).getToolWindow("CC Terminal")?.activate(null)
            if (isMultiLineMode) {
                inputPanel.requestInputFocus()
            } else {
                widget.requestFocusInWindow()
            }
        }
    }

    private fun sendToProcess(text: String) {
        session.sendText(text)
    }

    private fun toggleMode() {
        isMultiLineMode = !isMultiLineMode

        if (isMultiLineMode) {
            inputPanel.isVisible = true
            toggleModeButton.text = "Multi-line: ON"
            inputPanel.requestInputFocus()
        } else {
            inputPanel.isVisible = false
            toggleModeButton.text = "Multi-line: OFF"
            terminalWidget?.requestFocusInWindow()
        }
    }

    private fun registerKeyboardShortcut() {
        val inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        val actionMap = actionMap

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK or KeyEvent.SHIFT_DOWN_MASK), "toggleMode")
        actionMap.put("toggleMode", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                toggleMode()
            }
        })
    }

    override fun dispose() {
        session.stop()
    }
}
