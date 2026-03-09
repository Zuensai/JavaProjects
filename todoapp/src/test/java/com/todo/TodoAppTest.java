package com.todo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TodoAppTest { //test class


    @Test
    void TestAddTodo() {
        TodoApp app = new TodoApp();
        Todo t = app.addTodo("Hond wandelen");

        //controleer of het item is toegevoegd
        List<Todo> todos = app.getTodos();
        assertEquals(1, todos.size(), "should have 1 item");
        assertEquals("Hond wandelen", todos.get(0).getText(), "Text moet matchen");
        assertFalse(todos.get(0).isChecked(), "new todo should be unchecked");
    }
    @Test
    void testRemoveTodo() {
        TodoApp app = new TodoApp();
        Todo t1 = app.addTodo("task 1");
        Todo t2 = app.addTodo("task 2");

        boolean removed = app.removeTodo(t1.getId());
        assertTrue(removed, "remove should succeed");
        List<Todo> todos = app.getTodos();
        assertEquals(1, todos.size(), "should have 1 todo left");
        assertEquals("task 2", todos.get(0).getText(), "remaining todo should be task 2");
    }
    @Test
    void toggleTodo_shouldToggleChecked() {
        TodoApp app = new TodoApp();
        Todo t = app.addTodo("Hond wandelen");

        app.toggleTodo(t.getId());
        assertTrue(app.getTodos().get(0).isChecked());

        app.toggleTodo(t.getId());
        assertFalse(app.getTodos().get(0).isChecked());
    }


}
