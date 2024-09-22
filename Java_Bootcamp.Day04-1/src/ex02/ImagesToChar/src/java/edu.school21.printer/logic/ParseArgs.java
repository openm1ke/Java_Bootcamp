package edu.school21.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ParseArgs {
    @Parameter(names = { "--white", "-w" }, description = "Mask for white symbol", required = true)
    private String maskWhiteSymbol;

    @Parameter(names = { "--black", "-b" }, description = "Mask for black symbol", required = true)
    private String maskBlackSymbol;

    public String getMaskWhiteSymbol() {
        return maskWhiteSymbol;
    }

    public String getMaskBlackSymbol() {
        return maskBlackSymbol;
    }
}
