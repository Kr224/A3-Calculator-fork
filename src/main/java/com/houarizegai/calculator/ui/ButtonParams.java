package com.houarizegai.calculator.ui;

public class ButtonParams {
    String label;
    int x;
    int y;
    java.awt.event.ActionListener actionListener;
    boolean isVisible;

    // Constructor
    ButtonParams(String label, int x, int y, java.awt.event.ActionListener actionListener, boolean isVisible) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.actionListener = actionListener;
        this.isVisible = isVisible;
    }

}

