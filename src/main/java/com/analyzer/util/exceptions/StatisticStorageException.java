package com.analyzer.util.exceptions;

import java.nio.file.Path;

public class StatisticStorageException extends RuntimeException {

    public StatisticStorageException(Path path, Exception e) {
        super("Failed to write statistics to " + path, e);
    }
}
