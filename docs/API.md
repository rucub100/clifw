# API

### `CLI`

The default schema is *OPTIONS*.

| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `setArgs(String[] args)` | API entry point | arguments that were given to the program  | `ArgsCliBuilder` |
| `setArgs(String[] args, Schema schema)` | API entry point | arguments that were given to the program; schema  | `ArgsCliBuilder` |
| `useShell()` | API entry point | configures the interactive shell mode | `ShellCliBuilder` |
| `run()` | parse the arguments and run the CLI | - | - |
| `runShell()` | parse the arguments and start the shell mode | - | - |
| `hasResult()` | determin whether there is a result after the CLI has run | - | `boolean` |
| `getResult()` | Returns the result of the CLI run if there is one | - | `Result` |

### Builder (with fluent interface)

#### `ArgsCliBuilder`

| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `addOpt(Opt opt)` | add an option blueprint | option blueprint to add | `ArgsCliBuilder` |
| `addOpts(Opt... opts)` | add several option blueprints | option blueprints to add | `ArgsCliBuilder` |
| `addArg(Arg arg)` | add an argument blueprint | argument blueprint to add | `ArgsCliBuilder` |
| `addArgs(Arg... args)` | add several argument blueprints | argument blueprints to add | `ArgsCliBuilder` |
| `addCmd(Cmd cmd)` | add a command blueprint | command blueprint to add | `ArgsCliBuilder` |
| `addCmds(Cmd... cmds)` | add several command blueprints | command blueprints to add | `ArgsCliBuilder` |
| `build()` | build the CLI | - | `CLI` |

#### `ShellCliBuilder`

**TODO**

#### `OptBuilder`

The API is entered by calling `Opt.useChar(char opt)` or `Opt.useName(String name)`.

| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `shortId(char id)` | sets a short option identifier | a character to be set as the identifier | `OptBuilder` |
| `longId(String id)` | sets a long name identifier | a string to be set as the iddentifier | `OptBuilder` |
| `addArg(Arg arg)` | add an argument blueprint | argument blueprint to add | `OptBuilder` |
| `addArgs(Arg... args)` | add several argument blueprints | argument blueprints to add | `OptBuilder` |
| `description(String description)` | sets the description of the option | the description string | `OptBuilder` |
| `required()` | marks the option as mandatory | - | `OptBuilder` |
| `build()` | build the option blueprint | - | `Opt` |

#### `ArgBuilder`

The API is entered by calling `Arg.of(Class<?> clazz)`.

| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `placeholder(String placeholder)` | sets a placeholder text || `ArgBuilder` |
| `description(String description)` | sets a description text || `ArgBuilder` |
| `build()` | build the argument blueprint | - | `Arg` |

#### `CmdBuilder`

The API is entered by calling `Cmd.useName(String name)`.


| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `description(String description)` | sets a description text | the description | `CmdBuilder` |
| `addOpt(Opt opt)` | add an option blueprint | option blueprint to add | `CmdBuilder` |
| `addOpts(Opt... opts)` | add several option blueprints | option blueprints to add | `CmdBuilder` |
| `addArg(Arg arg)` | add an argument blueprint | argument blueprint to add | `CmdBuilder` |
| `addArgs(Arg... args)` | add several argument blueprints | argument blueprints to add | `CmdBuilder` |
| `build()` | build the command blueprint | - | `Cmd` |

### The Result object `Result`

**TODO**
