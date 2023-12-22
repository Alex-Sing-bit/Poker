package ru.vsu.cs.baklanova.GameDrawing;

import ru.vsu.cs.baklanova.GameDrawing.Events.GameEvent;

import java.util.Stack;

public class StackDrawing {
    public static void stackDraw(Stack<GameEvent> stack) {
        while (!stack.empty()) {
            stack.pop().draw();
        }
    }
}
