package edu.school21.printer.app;

import edu.school21.printer.logic.Converter;
import edu.school21.printer.logic.ParseArgs;

import com.beust.jcommander.JCommander;

class Program {

    public static void main(String[] args) {
        
        ParseArgs myArgs = new ParseArgs();
        JCommander jCommander = JCommander.newBuilder().addObject(myArgs).build();

        jCommander.parse(args);
        
        Converter converter = new Converter(myArgs.getMaskWhiteSymbol(), myArgs.getMaskBlackSymbol(), "./src/resources/it.bmp");
        converter.convert();
    }
}