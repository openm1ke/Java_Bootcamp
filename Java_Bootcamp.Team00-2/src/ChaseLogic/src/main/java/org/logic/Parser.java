package org.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators="=")
public class Parser {
    @Parameter(names = "--enemiesCount", description = "Enemies count", required = true)
    public Integer enemiesCount;

    @Parameter(names = "--wallsCount", description = "Wall count", required = true)
    public Integer wallsCount;

    @Parameter(names = "--size", description = "Size map", required = true)
    public Integer size;

    @Parameter(names = "--profile", description = "Profile", required = true)
    public String profile;
}
