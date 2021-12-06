package com.pusio.day7.part1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProblemSolver {
    public Integer solve(File file) throws IOException {
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        return 0;
    }
}
