package com.homeenv.Interfaces.impl;

import com.homeenv.Interfaces.IMimeTypeExtractor;

import java.io.File;
import java.util.Optional;

/**
 * Created by skhodukin on /67/17.
 */
public abstract class Extractor {
    private IMimeTypeExtractor mimeTypeExtractor;

    public Extractor(IMimeTypeExtractor mimeTypeExtractor) {
        this.mimeTypeExtractor = mimeTypeExtractor;
    }

    public void setMimeTypeExtractor(IMimeTypeExtractor mimeTypeExtractor) {
        this.mimeTypeExtractor = mimeTypeExtractor;
    }

    public Optional<String> extractMimeType(File file){
        return mimeTypeExtractor.mimeExtract(file);
    }
}
