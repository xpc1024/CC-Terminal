package com.lightterminal

import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.*

class MultiLineInputPanel(
    private val onSend: (String) -> Unit
) : JPanel(BorderLayout()) {

    private val textArea = JTextArea(4, 80)
    private val sendButton = JButton("Send")

    init {
        border = BorderFactory.createTitledBorder("Input (Enter=Send, Shift+Enter=NewLine)")

        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.font = textArea.font.deriveFont(14f)
        val scrollPane = JScrollPane(textArea)
        add(scrollPane, BorderLayout.CENTER)

        val toolbar = JPanel(FlowLayout(FlowLayout.LEFT))
        toolbar.add(sendButton)
        add(toolbar, BorderLayout.SOUTH)

        val inputMap = textArea.getInputMap(JComponent.WHEN_FOCUSED)
        val actionMap = textArea.actionMap

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "send")
        actionMap.put("send", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                sendText()
            }
        })

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK), "insert-newline")
        actionMap.put("insert-newline", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                textArea.replaceSelection("\n")
            }
        })

        sendButton.addActionListener {
            sendText()
        }
    }

    private fun sendText() {
        val text = textArea.text.trimEnd()
        if (text.isNotEmpty()) {
            onSend(text)
            textArea.text = ""
        }
    }

    fun requestInputFocus() {
        textArea.requestFocusInWindow()
    }

    fun setText(text: String) {
        textArea.text = text
    }

    fun getText(): String = textArea.text
}
