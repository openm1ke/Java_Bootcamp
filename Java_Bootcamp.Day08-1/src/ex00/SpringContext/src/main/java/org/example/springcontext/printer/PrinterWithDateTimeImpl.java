package org.example.springcontext.printer;

import org.example.springcontext.renderer.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {

    private final Renderer renderer;
    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }
    @Override
    public void print(String text) {
        renderer.render(LocalDateTime.now() + " " + text);
    }
}
