package com.homeenv.Interfaces;

import java.io.File;
import java.util.Optional;

/**
 * Created by skhodukin on /57/17.
 */
@FunctionalInterface
public interface IMimeTypeExtractor {
    Optional<String> mimeExtract(File file);
}
