# clifw
Yet another ultra lightweight CLI framework for parsing command line arguments passed to programs.<br>
The API also allows you to build an interactive shell.

## Example
The main public API `CLI` allows you to build a schema for the expected arguments.<br>
This is a convenient solution that offers a modern, descriptive and fluent manner.

```java
public static void main(String[] args) {
    CLI cli = CLI.setArgs(args) // args = new String[] { "-a" }
        .addOption(Opt
            .useChar('a')
            .description("this is a short test option")
            .build())
        .build();
    ...
}
```

Once you've defined the schema, call the `run` method and let the framework do it's job.

```java
try {
    cli.run();
    ...
} catch(Exception e) {
    System.err.println(e.getMessage());
}
```

The last step is to process the result `cli.getResult()` in your own business code.<br>
For an example of an interactive shell, read the [shell](docs/SHELL.md) documentation.

## General Design

The main building block is the schema which consists of different other sub building blocks and glue components.

### Schema

| Name                  | Pattern                 | Examples                                       | Description                                                                                                                                                                                                                                                                                                                                                                                                                         |
|-----------------------|-------------------------|------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ***Options***         | `[-<o>\|--<option>]...`     | `-r`<br>`--recursive`<br>`-o=filename`<br>`-i name` | This schema allows to define an arbitrary sequence of options. An option is either a single character with a preceding `-` or any word with a preceding `--`. An optional value can be assigned, either in the mathematical notation with the `=` sign or simply separated by a *space*. Obviously, further restrictions on the value must be made, for example it must not start with a dash or contain spaces. |
| ***Arguments***       | `[<arg>]...`              | `1 2 3`<br>`"My Name"`                         | Numbers or words can be used as arguments. If a single argument contains spaces, it must be enclosed in quotation marks. |
| ***Options and arguments*** | `[-<o>\|--<option>]... [<arg>]...` | `-c 3 output.txt`                   | The order is important here, the first argument may only come after the last option including its value, if any. |
| ***Commands***        | `<command> [-<o>\|--<option>]... [<arg>]...` | `checkout -b development`         | Note that at most one command can be passed as an argument per call. However, any number of different commands can of course be defined in the API. |
| ***Shell***           |                         |                                                | Behaves like if you put the *Commands* scheme in a loop. Before entering the interactive shell, the *Options and arguments* schema applies. |

The `setArgs` and `useShell` methods initiate the declarative definition of the schema. In the first method, a schema can be passed as an optional argument, the default value corresponds to "*Options*". However, a builder object is returned, which is also implemented as a fluent interface. See [API](docs/API.md) section for more details.
