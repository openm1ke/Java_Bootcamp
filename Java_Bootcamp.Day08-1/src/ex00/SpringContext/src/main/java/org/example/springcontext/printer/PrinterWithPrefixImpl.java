package org.example.springcontext.printer;

import org.example.springcontext.renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer {

    private final Renderer renderer;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String text) {
        renderer.render("Prefix: " + text);
    }
}
