# vscode task

定义在`.vscode/tasks.json`文件中的任务, 可能通过`C+S+P -> Tasks: Configure Task Runner`
来启动一个简单任务. 通过`C+P -> task `来选择运行的任务.

## 任务定义

```json
// echo "hello world"
{
    "version": "0.1.0",
    "command": "echo",
    "isShellCommand": true,
    "args": ["Hello World"],  // 全局参数
    "showOutput": "always"  // 总是显示输出
}
```

定义相同命令的多个任务

```json
// echo "Hello World"
{
    "version": "0.1.0",
    "command": "echo",
    "isShellCommand": true,
    "args": [],
    "showOutput": "always",
    "echoCommand": true,  // 会显示执行的具体命令
    "suppressTaskName": true,  // taskname不会成为参数的一部分
    "tasks": [
        {
            "taskName": "hello",
            "args": ["Hello World"]
        },
        {
            "taskName": "bye",
            "args": ["Good Bye"]
        }
    ]
}
```

定义不同命令的多个任务

```json
{
    "version": "0.1.0",
    "tasks": [
        {
            "taskName": "tsc",
            "command": "tsc",
            "args": ["-w"],
            "isShellCommand": true,
            "isBackground": true,
            "problemMatcher": "$tsc-watch"
        },
        {
            "taskName": "build",
            "command": "gulp",
            "args": ["build"],
            "isShellCommand": true
        }
    ]
}
```

定义操作系统相关的命令和属性, `windows/linux/osx`

```json
{
    "version": "0.1.0",
    "windows": {
        "command": "C:\\Program Files\\nodejs\\node.exe",
        "showOutput": "always"
    },
    "linux": {
        "command": "/usr/bin/node"
    }
}
```

## 变量替换

* `${workspaceRoot}`, 打开的目录, 绝对路径
* `${workspaceRootFolderName}`, 打开的目录名
* `${file}`, 打开的文件路径, 绝对路径
* `${relativeFile}`, 打开的文件路径, 相对路径
* `${fileBasename}`, 打开的文件的文件名
* `${fileBasenameNoExtension}`, 打开的文件名, 无扩展名
* `${fileDirname}`, 打开的文件的目录, 绝对路径
* `${fileExtname}`, 打开的文件的扩展名
* `${cwd}`, 命令的启动路径
* `${lineNumber}`, 当前选择的行号
* `${env.Name}`, 环境变量名