package edu.school21.printer.app;

import edu.school21.printer.logic.Converter;

class Program {

    public static void main(String[] args) {
        String maskWhiteSymbol = ".";
        String maskBlackSymbol = "0";
        if (args.length != 3) {
            System.out.println("Usage: app <mask_white_symbol> <mask_black_symbol> <full_path_to_image>");
            return;
        } else {
            maskWhiteSymbol = args[0];
            maskBlackSymbol = args[1];
            String path = args[2];
            Converter converter = new Converter(maskWhiteSymbol, maskBlackSymbol, path);
            converter.convert();
        }
    }
}