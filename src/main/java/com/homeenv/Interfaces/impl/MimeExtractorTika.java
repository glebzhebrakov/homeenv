/*
for alternate future realization

package com.homeenv.Interfaces.impl;

import com.homeenv.Interfaces.IMimeTypeExtractor;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.external.ExternalParser;
import org.springframework.stereotype.Service;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.Optional;


*//**
 * Created by skhodukin on /57/17.
 *//*
@Service
public class MimeExtractorTika extends FileTypeDetector implements IMimeTypeExtractor {
    private final Tika tika = new Tika();

    public MimeExtractorTika() {
        super();
    }

    @Override
    public String probeContentType(Path path) throws IOException {
        // Try to detect based on the file name only for efficiency
        String fileNameDetect = tika.detect(path.toString());
        if(!fileNameDetect.equals(MimeTypes.OCTET_STREAM)) {
            return fileNameDetect;
        }

        // Then check the file content if necessary
        String fileContentDetect = tika.detect(path.toString());
        if(!fileContentDetect.equals(MimeTypes.OCTET_STREAM)) {
            return fileContentDetect;
        }

        // Specification says to return null if we could not
        // conclusively determine the file type
        return null;
    }

    @Override
    public Optional<String> mimeExtract(File file) {
        try {
            return Optional.ofNullable(probeContentType(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    } }*/

    /*  @Override
    public Optional<String> mimeExtract(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            ContentHandler contentHandler = new BodyContentHandler();
            ParseContext parseContext = new ParseContext();
            Metadata metadata = new Metadata();
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            Parser parser = new AutoDetectParser();         //!!!!!!Exception
            parser.parse(fileInputStream, contentHandler, metadata, parseContext);
            return Optional.ofNullable(metadata.get(Metadata.CONTENT_TYPE));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return Optional.empty();
    }*/

