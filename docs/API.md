# API

### `CLI`

| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `setArgs(String[] args)` | API entry point | arguments that were given to the program  | `ArgsCliBuilder` |
| `setArgs(String[] args, Schema schema)` | API entry point | arguments that were given to the program; schema  | `ArgsCliBuilder` |
| `useShell()` | API entry point | configures the interactive shell mode | `ShellCliBuilder` |
| `run()` | parse the arguments and run the CLI | - | - |
| `runShell()` | parse the arguments and start the shell mode | - | - |
| `hasResult()` | determin whether there is a result after the CLI has run | - | boolean |
| `getResult()` | Returns the result of the CLI run if there is one | - | `Result` |

### Builder (with fluent interface)

#### `ArgsCliBuilder`

#### `ShellCliBuilder`

#### `Option`

#### `Command`

### `Result`
