package com.homeenv.Interfaces.impl;

import com.homeenv.Interfaces.IMimeTypeExtractor;

import java.io.File;
import java.util.Optional;

/**
 * Created by skhodukin on /67/17.
 */
public class MimeExtractorDefault implements IMimeTypeExtractor{
    @Override
    public Optional<String> mimeExtract(File file) {
            try {
                return Optional.ofNullable(java.nio.file.Files.probeContentType(file.toPath()));
            } catch (Exception e) {
                e.printStackTrace();
                //log.error("unable to detect mime type of file " + file.getAbsolutePath(), e);
            }

            return Optional.empty();
        }
    }
