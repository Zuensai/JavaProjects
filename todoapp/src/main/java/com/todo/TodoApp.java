package com.todo;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class TodoApp {

    private final List<Todo> todos = new ArrayList<>();
    private int nextId = 1;

    public Todo addTodo(String text) {
        Todo t = new Todo(nextId, text, false);
        todos.add(t);
        nextId++;
        return t;
    }

    public boolean removeTodo(int id) {
        return todos.removeIf(t -> t.getId() == id);
    }

    public boolean toggleTodo(int id) {
        for (Todo t : todos) {
            if (t.getId() == id) {
                t.toggleChecked();
                return true;
            }
        }
        return false;
    }

    public List<Todo> getTodos() {
        return new ArrayList<>(todos); // safe copy
    }

    public void saveTodos(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Todo t : todos) {
                writer.write(t.getId() + "|" + t.getText() + "|" + t.isChecked() + "\n");
            }
        }
    }

    public void loadTodos(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            todos.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                String text = parts[1];
                boolean checked = Boolean.parseBoolean(parts[2]);
                todos.add(new Todo(id, text, checked));
                if (id >= nextId) nextId = id + 1;
            }
        }
    }

    public static void main(String[] args) {
        TodoApp app = new TodoApp();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===TO DO (amazing) APP ===");
            System.out.println("1. Add items to the list");
            System.out.println("2. Remove item from the list");
            System.out.println("3. Check or uncheck a to-do item");
            System.out.println("4. Show all to-do items");
            System.out.println("5. Exit this beautiful program");
            System.out.println("6. Don't forget to save!");
            System.out.println("7. Loading ...");
            System.out.print("What are we doing today? (please pick one): ");

            int choice;
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Please use numbers... you know what numbers are?");
                    scanner.nextLine();
                    continue;
                }
                scanner.nextLine();


            if (choice == 1) {
                System.out.println("What would you like to get done today?");
                String todoText = scanner.nextLine();
                Todo t = app.addTodo(todoText);
                System.out.println("Added #" + t.getId());
            } else if (choice == 2) {
                List<Todo> list = app.getTodos();
                System.out.println("\nYour amazing list: ");
                for (int i = 0; i < list.size(); i++){
                    System.out.println(list.get(i));
                }
                System.out.println("what number to remove: ");
                int idToRemove = scanner.nextInt();
                scanner.nextLine();
                boolean removed = app.removeTodo(idToRemove);
                if (removed) System.out.println("item is removed!");
                else System.out.println("Item not found!");
            } else if (choice == 3) {
                System.out.println("Checked/Unchecked");
                System.out.println("Enter ID of item to Check/Uncheck");
                int idToToggle = scanner.nextInt();
                scanner.nextLine();
                boolean toggle = app.toggleTodo(idToToggle);
                if (toggle) System.out.println("Toggled!");
                else System.out.println("not found!");
            } else if (choice == 4) {
                System.out.println("Display all yo amazing tasks: ");
                List<Todo> list = app.getTodos();
                for (int i = 0; i < list.size(); i++){
                    System.out.println(list.get(i));
                }
            } else if (choice == 5) {
                running = false;
                System.out.println("bu-bye!");
            } else if (choice == 6) {
                System.out.println("Saving ... don't turn off the power!");
                try {
                    app.saveTodos("todos.csv");
                    System.out.println("Saved! We did it!");
                } catch (IOException error) {
                    System.out.println("Error saving: " + error.getMessage());
                }
            } else if (choice == 7) {
                System.out.println("Loading...");
                try {
                    app.loadTodos("todos.csv");
                    System.out.println("We found yo list!");
                } catch (FileNotFoundException error) {
                    System.out.println("u forgot to save maybe? could not find savefile.");
                } catch (IOException error) {
                    System.out.println("Error loading file: " + error.getMessage());
                }
            } else {
                System.out.println("Not the brightest lightbulb? okay, please select number 1 - 7");
            }
        }
        scanner.close();
    }
}