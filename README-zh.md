# CC Terminal

#### 介绍

CC Terminal 是一款超轻量级的 IntelliJ IDEA 插件，为 Claude Code 提供原生终端使用体验。零额外资源开销，即装即用，0 上手成本。

#### 特性

- **超轻量**：插件体积仅约 1.6MB，无任何外部依赖，不占用额外资源
- **原生体验**：直接复用 IDEA 内置 Terminal 引擎，与 IDEA 自带终端完全一致的渲染和交互体验
- **一键启动**：内置 `claude --dangerously-skip-permissions` 模式，一键启动，无需每次手动输入
- **富文本输入**：支持多行文本输入，粘贴带换行符的内容不会自动发送，避免误操作
- **灵活切换**：Multi-line 模式下 Enter 发送、Shift+Enter 换行；关闭后切换为原生终端交互，支持方向键选择选项
- **文件附件**：点击 `+` 按钮可添加任意格式的文件，文件路径自动插入输入框，发送给 Claude Code 以供读取

#### 软件架构

基于 IntelliJ Platform Plugin SDK 开发，使用 Kotlin 语言，复用 IDEA 内置的 `TerminalView` / `ShellTerminalWidget` 终端引擎。

```
src/main/kotlin/com/lightterminal/
├── LightTerminalFactory.kt    # ToolWindow 工厂
├── LightTerminalPanel.kt      # 主面板（工具栏 + 输入区 + 终端容器）
├── MultiLineInputPanel.kt     # 多行富文本输入面板
├── TerminalSession.kt         # 终端会话管理
└── CommandPresets.kt          # 命令预设（claude / claude --dangerously-skip-permissions）
```

#### 安装教程

1.  从 [Releases](https://github.com/xxx/cc-terminal/releases) 下载最新的 `ccterminal-x.x.x.zip`
2.  打开 IDEA → `File` → `Settings` → `Plugins` → ⚙️ → `Install Plugin from Disk...`
3.  选择下载的 zip 文件，重启 IDEA 即可

#### 使用说明

1.  重启后，在 IDEA 底部工具栏找到 **CC Terminal** 面板
2.  从下拉框选择命令（默认 `claude --dangerously-skip-permissions`），点击 **Run** 启动
3.  **Multi-line: ON**（默认）：
    - 在上方输入框中输入内容
    - 按 `Enter` 发送给 Claude Code
    - 按 `Shift + Enter` 换行
    - 粘贴多行内容不会自动发送
    - 点击 **Send** 按钮发送
4.  **Multi-line: OFF**：
    - 输入框隐藏，直接在终端中操作
    - 支持方向键、回车键等原生终端交互
5.  按 `Ctrl + Shift + M` 可快速切换 Multi-line 模式

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

#### 许可证

[MIT License](LICENSE)
