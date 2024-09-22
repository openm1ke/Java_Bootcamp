package edu.school21.printer.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
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
            BufferedImage image = ImageIO.read(new File(path));
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    if (image.getRGB(j, i) == Color.BLACK.getRGB()) {
                        System.out.print(maskBlackSymbol);
                    } else {
                        System.out.print(maskWhiteSymbol);
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
