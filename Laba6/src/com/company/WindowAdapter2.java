package com.company;

import java.awt.event.*;

public class WindowAdapter2 extends WindowAdapter {
    public void windowClosing (WindowEvent wE) {
        System.exit (0);
    }
}
