package com.homeenv.Interfaces.impl;

import com.homeenv.Interfaces.IMimeTypeExtractor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Optional;


/**
 * Created by skhodukin on /67/17.
 */
@Service
public class MimeExtractor implements IMimeTypeExtractor {


    @Override
    public Optional<String> mimeExtract(File file) {
        try {
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            String type = fileNameMap.getContentTypeFor(String.valueOf(file));

            return Optional.ofNullable(type);
        } catch (Exception e) {
            e.printStackTrace();
            //log.error("unable to detect mime type of file " + file.getAbsolutePath(), e);
        }

        return Optional.empty();
    }
}
