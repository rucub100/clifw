# Interactive Shell

You can find an example [here](../examples/example-01/src/main/java/de/curbanov/clifw/example/Example.java) to try it out.
As a developer, focus on your business code and let the framework do the work:

```java
public static void main(String[] args) {
    CLI.useShell()
        .addCmd(Cmd
            .useName("mycommand")
            .consumer(Example::onMyCommand)
            .build())
        .addCmd(Cmd
            .useName("sayhi")
            .consumer(x -> System.out.println("Hi!"))
            .build())
        .build()
        .run();
}

private static void onMyCommand(Command command) { ... }
...
```

It really doesn't get any easier than this!

## Features

- Prepare your program with a callback function or a lambda expression, the `whenEntering` API makes it possible.
- The `globalLevelCommandConsumer` API enables commands to be processed on a global level.
