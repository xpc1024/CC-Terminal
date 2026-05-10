# CC Terminal

#### Introduction

CC Terminal is an ultra-lightweight IntelliJ IDEA plugin that provides a native terminal experience for Claude Code. Zero extra resource overhead, install and use immediately, zero learning curve.

#### Features

- **Ultra-lightweight**: Only ~1.6MB in size, no external dependencies, no additional resource consumption
- **Native experience**: Built on IDEA's built-in Terminal engine, delivering the exact same rendering and interaction as IDEA's native terminal
- **One-click launch**: Built-in `claude --dangerously-skip-permissions` mode — one click to start, no manual typing needed
- **Rich text input**: Multi-line text input support — pasting content with line breaks won't auto-send, preventing accidental submissions
- **Flexible switching**: In Multi-line mode, Enter sends, Shift+Enter inserts a newline; turn it off to switch to native terminal interaction with arrow key navigation
- **File attachment**: Click the `+` button to attach any file — the file path is automatically inserted into the input area and sent to Claude Code for reference

#### Architecture

Built on IntelliJ Platform Plugin SDK with Kotlin, leveraging IDEA's built-in `TerminalView` / `ShellTerminalWidget` terminal engine.

```
src/main/kotlin/com/lightterminal/
├── LightTerminalFactory.kt    # ToolWindow factory
├── LightTerminalPanel.kt      # Main panel (toolbar + input area + terminal container)
├── MultiLineInputPanel.kt     # Multi-line rich text input panel
├── TerminalSession.kt         # Terminal session management
└── CommandPresets.kt          # Command presets (claude / claude --dangerously-skip-permissions)
```

#### Installation

1.  Download the latest `ccterminal-x.x.x.zip` from [Releases](https://github.com/xxx/cc-terminal/releases)
2.  Open IDEA → `File` → `Settings` → `Plugins` → ⚙️ → `Install Plugin from Disk...`
3.  Select the downloaded zip file and restart IDEA

#### Usage

1.  After restart, find the **CC Terminal** panel in IDEA's bottom toolbar
2.  Select a command from the dropdown (default: `claude --dangerously-skip-permissions`), click **Run** to start
3.  **Multi-line: ON** (default):
    - Type content in the input area above
    - Press `Enter` to send to Claude Code
    - Press `Shift + Enter` for a new line
    - Pasting multi-line content will not auto-send
    - Click the **Send** button to send
4.  **Multi-line: OFF**:
    - Input area is hidden, interact directly in the terminal
    - Full native terminal interaction with arrow keys, Enter, etc.
5.  Press `Ctrl + Shift + M` to quickly toggle Multi-line mode

#### Contributing

1.  Fork this repository
2.  Create a new Feat_xxx branch
3.  Commit your code
4.  Create a new Pull Request

#### License

[MIT License](LICENSE)
