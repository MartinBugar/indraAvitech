package com.martyx;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Application.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {

        List<String> commands = Arrays.asList(
                "Add(1, \"a1\",\"Martin\")",
                "Add(2, \"a2\",\"Robert\")",
                "PrintAll",
                "DeleteAll",
                "PrintAll"
        );

        SynchronousQueue<String> queueCommands = new SynchronousQueue<>(true);

        // Producing threads
        for (String command : commands) {
            addCommandToQueue(queueCommands, command);
        }

        // Consuming threads
        for (int i = 0; i < commands.size(); i++) {
            new Thread(() -> dequeue(queueCommands)).start();
            Thread.sleep(500);
        }

    }

    public static void addCommandToQueue(SynchronousQueue<String> queueCommands, String command) throws InterruptedException {
        new Thread(() -> enqueue(queueCommands, command)).start();
        Thread.sleep(500);
    }

    public static void enqueue(SynchronousQueue<String> queue, String element) {
        try {
            queue.put(element);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static List<UserDefinition> dequeue(BlockingQueue<String> queue) {
        try {
            String element = queue.take();
            log("queue.take() returned %s", element);
            CommandProcessor commandProcessor = new CommandProcessor();
            List<UserDefinition> userDefinitions = commandProcessor.processCommand(element);
            System.out.println();
            return userDefinitions;
        } catch (InterruptedException | SQLException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }


    private static void log(String format, Object... args) {
        System.out.printf(Locale.US, "[%-9s] %s%n", Thread.currentThread().getName(), String.format(format, args));

    }

}
