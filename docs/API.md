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


| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `whenEntering(Consumer<Result> consumer)` | sets a customer to be called when entering the shell | the consumer | `ShellCliBuilder` |
| `globalLevelCommandConsumer(Consumer<Command> consumer)` | sets a customer to be called for each command entered | the consumer | `ShellCliBuilder` |
| `build()` | build the CLI | - | `CLI` |

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
| `consumer(Consumer<Command> consumer)` | sets a consumer to be called for the given command | the consumer | `CmdBuilder` |
| `build()` | build the command blueprint | - | `Cmd` |

### The Result object `Result`

| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `hasOptions()` | gets a value determine whether the result contains user options | - | `boolean` |
| `getOptions()` | gets a list of parsed user options | - | `List<Option>` |
| `hasArguments()` | gets a value determine whether the result contains parsed arguments | - | `boolean` |
| `getArguments()` | gets a list of parsed user arguments | - | `List<Argument<?>>` |
| `isCommand()` | gets a value determine whether the given result has parsed a command; if `true`, `hasOptions()` and `hasArguments()` is `false` but the `Command` can contain its own options and arguments.  | - | `boolean` |
| `getCommand` | gets the parsed user command | - | `Command` |

#### `Command`

| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `getBlueprint()` | gets the corresponding blueprint object | - | `Cmd` |
| `hasOptions()` | gets a value determine whether the command contains options | - | `boolean` |
| `getOptions()` | gets the options parsed with the given command | - | `List<Option>` |
| `hasArguments()` | gets a value determine whether the command contains arguments | - | `boolean` |
| `getArguments()` | gets the arguemnts parsed with the given command | - | `List<Argument<?>>` |

#### `Option`

| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `getBlueprint()` | gets the corresponding blueprint object | - | `Opt` |
| `getId()` | gets the identifier string | - | `String` |
| `hasArguments()` | gets a value determine whether the option contains arguments | - | `boolean` |
| `getArguments()` | gets the arguemnts parsed with the given option | - | `List<Argument<?>>` |

#### `Argument`

| Method | Description | Parameter | Return |
|--------|-------------|-----------|--------|
| `getBlueprint()` | gets the corresponding blueprint object | - | `Arg` |
| `getValue()` | gets the argument value | - | generic type |

