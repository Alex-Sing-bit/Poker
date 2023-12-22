package ru.vsu.cs.baklanova.GameDrawing.Events;

import java.util.Stack;

public interface GameEvent {
    void draw();

    Stack<GameEvent> stack = new Stack<>();

    default void saveEventAndDrawIt() {
        System.out.println("-".repeat(30));
        this.draw();
        System.out.println();
        stack.push(this);
    }
}
