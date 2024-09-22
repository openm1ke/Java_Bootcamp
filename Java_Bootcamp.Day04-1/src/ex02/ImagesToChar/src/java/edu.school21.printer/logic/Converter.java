package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Converter {
    String maskWhiteSymbol;
    String maskBlackSymbol;
    String path;

    public Converter(String maskWhiteSymbol, String maskBlackSymbol, String path) {
        this.maskWhiteSymbol = maskWhiteSymbol;
        this.maskBlackSymbol = maskBlackSymbol;
        this.path = path;
    }

    public void convert() {
        try {
            ColoredPrinter console = new ColoredPrinter.Builder(1, false)
                    .foreground(Ansi.FColor.valueOf(maskBlackSymbol))
                    .background(Ansi.BColor.valueOf(maskWhiteSymbol))
                    .build();
            try {
                BufferedImage image = ImageIO.read(new File(path));
                for (int i = 0; i < image.getWidth(); i++) {
                    for (int j = 0; j < image.getHeight(); j++) {
                        if (image.getRGB(j, i) == Color.BLACK.getRGB()) {
                            console.print("█");
                        } else {
                            console.print(" ");
                        }
                    }
                    System.out.println();
                }
            } catch (IOException e) {
                System.out.println("Failed to read image: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Failed to create ColoredPrinter: " + e.getMessage());
        }
    }
}
