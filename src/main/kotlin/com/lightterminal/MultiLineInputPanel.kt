package com.lightterminal

import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*

class MultiLineInputPanel(
    private val onSend: (String) -> Unit
) : JPanel(BorderLayout()) {

    private val textArea = JTextArea(4, 80)
    private val sendButton = JButton("Send")
    private val attachButton = JButton("+")

    init {
        border = BorderFactory.createTitledBorder("Input (Enter=Send, Shift+Enter=NewLine)")

        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.font = textArea.font.deriveFont(14f)
        val scrollPane = JScrollPane(textArea)
        add(scrollPane, BorderLayout.CENTER)

        attachButton.toolTipText = "Attach file"
        attachButton.preferredSize = java.awt.Dimension(sendButton.preferredSize.width - 20, sendButton.preferredSize.height)
        attachButton.addActionListener { chooseFile() }

        val toolbar = JPanel(FlowLayout(FlowLayout.LEFT))
        toolbar.add(attachButton)
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

    private fun chooseFile() {
        val chooser = JFileChooser()
        chooser.dialogTitle = "Choose file"
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            val path = chooser.selectedFile.absolutePath
            val current = textArea.text
            textArea.text = if (current.isEmpty()) " $path " else "${current.trimEnd()} $path "
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
