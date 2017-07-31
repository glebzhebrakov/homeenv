package com.homeenv.service;


import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.file.FileMetadataDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.homeenv.Interfaces.IMimeTypeExtractor;
import com.homeenv.Interfaces.impl.*;
import com.homeenv.config.ApplicationProperties;
import com.homeenv.domain.Image;
import com.homeenv.domain.ImageAttribute;
import com.homeenv.domain.ImageDuplicate;
import com.homeenv.messaging.IndexingRequest;
import com.homeenv.repository.ImageAttributeRepository;
import com.homeenv.repository.ImageDuplicateRepository;
import com.homeenv.repository.ImageRepository;
import com.homeenv.util.HomeenvImageMetadataReader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class ImageMetadataService {

    private Logger log = LoggerFactory.getLogger(ImageMetadataService.class);

    private final ApplicationProperties applicationProperties;

    private final ImageRepository imageRepository;

    private final ImageDuplicateRepository imageDuplicateRepository;

    private final ImageAttributeRepository imageAttributeRepository;

    private final MessagingService messagingService;

    private Extractor mimeExtractor = new MimeTypeExtractor(new MimeExtractorTika()); //select mime detect realization

    @Autowired
    public ImageMetadataService(ApplicationProperties applicationProperties,
                                ImageRepository imageRepository,
                                ImageDuplicateRepository imageDuplicateRepository,
                                ImageAttributeRepository imageAttributeRepository,
                                MessagingService messagingService) {
        this.applicationProperties = applicationProperties;
        this.imageRepository = imageRepository;
        this.imageDuplicateRepository = imageDuplicateRepository;
        this.imageAttributeRepository = imageAttributeRepository;
        this.messagingService = messagingService;
    }

    public void indexStorage(){
        Collection<File> files = FileUtils.listFiles(new File(applicationProperties.getIndexing().getPath()),
                null,
                applicationProperties.getIndexing().getRecursive());

        files.forEach( //file -> detectMimeType(file)
                file-> mimeExtractor.extractMimeType(file)
                 .ifPresent(mime -> {
          if (mime.startsWith("image")){

              calculateHash(file).ifPresent(hash -> {
                  Image maybeIndexedImage = imageRepository.findByHash(hash).orElse(null);
                  Set<ImageDuplicate> duplicates = new HashSet<>();
                  Set<ImageAttribute> imageAttributes = new HashSet<>();
                    if (maybeIndexedImage == null){
                        maybeIndexedImage = new Image()
                                .withPath(file.getAbsolutePath())
                                .withHash(hash)
                                .withMime(mime)
                                .withIndexed(false);

                        log.info("#### Indexing : " + maybeIndexedImage.getPath());

                        messagingService.sendIndexingRequest(new IndexingRequest(
                                maybeIndexedImage.getPath(),
                                maybeIndexedImage.getHash(),
                                applicationProperties.getIndexing().getPath()
                        ));
                        imageAttributes = extractMetadata(file);
                    } else {
                        log.info(String.format("### Found duplicates : \n original  %s , \n duplicate %s", maybeIndexedImage.getPath(), file.getAbsolutePath()));
                        duplicates.add(new ImageDuplicate(file.getAbsolutePath()));
                    }

                  saveImage(maybeIndexedImage, duplicates, imageAttributes);

              });
          }


        }));
    }

    @Transactional
    private void saveImage(final Image image, final Set<ImageDuplicate> duplicates, final Set<ImageAttribute> imageAttributes ){
        try {
            Image img = imageRepository.save(image);
            if (duplicates != null){
                duplicates.forEach(imageDuplicate -> imageDuplicate.setImage(img));
                imageDuplicateRepository.save(duplicates);
            }

            if (imageAttributes != null){
                imageAttributes.forEach(imageAttribute -> imageAttribute.setImage(image));
                imageAttributeRepository.save(imageAttributes);
            }

        } catch (Exception e){
            log.warn("unable to save image " + e.getMessage());
        }
    }

    private Optional<String> calculateHash(File file){
        try {
            return Optional.of(Files.hash(file, Hashing.md5()).toString());
        } catch (IOException e) {
            log.error("unable to calculate hash code " + file.getAbsolutePath(), e);
        }
        return Optional.empty();
    }


/*    private Optional<String> detectMimeType(File file){
        try {
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            String type = fileNameMap.getContentTypeFor(String.valueOf(file));

            return Optional.ofNullable(type);

            //return Optional.ofNullable("image");
        } catch (Exception e) {
            log.error("unable to detect mime type of file " + file.getAbsolutePath(), e);
        }

        return Optional.empty();
    }*/

    private Set<ImageAttribute> extractMetadata(File file){
        Set<ImageAttribute> attributes = new HashSet<>();
        try {

            Metadata metadata = HomeenvImageMetadataReader.readMetadata(file);
            attributes.addAll(extractDirectoryAttributes(metadata, GpsDirectory.class));
            attributes.addAll(extractDirectoryAttributes(metadata, FileMetadataDirectory.class));
            attributes.addAll(extractDirectoryAttributes(metadata, JpegDirectory.class));

        } catch (Throwable e) {
            log.error("unable extract metadata from file " + file.getAbsolutePath(), e);
        }
        return attributes;
    }

    private Set<ImageAttribute> extractDirectoryAttributes(final Metadata metadata, final Class<? extends Directory> dirCls) {
        Set<ImageAttribute> attributes = new HashSet<>();
        metadata.getDirectoriesOfType(dirCls)
                .forEach(directory -> directory.getTags()
                        .forEach(
                                tag -> attributes.add(new ImageAttribute(
                                        tag.getTagName(),
                                        tag.getDescription())
                                )
                        )
                );

        return attributes;
    }

    @Deprecated
    private Set<ImageAttribute> extractImageDimension(final Metadata metadata){
        Set<ImageAttribute> attributes = new HashSet<>();
        metadata.getDirectoriesOfType(JpegDirectory.class)
                .forEach(directory -> directory.getTags()
                        .forEach(
                                tag -> attributes.add(new ImageAttribute(
                                        tag.getTagName(),
                                        tag.getDescription())
                                )
                        )
                );

        return attributes;
    }


    @Deprecated
    private Set<ImageAttribute> extractFileSize(final Metadata metadata){
        Set<ImageAttribute> attributes = new HashSet<>();
        metadata.getDirectoriesOfType(FileMetadataDirectory.class)
                .forEach(directory -> directory.getTags()
                        .forEach(
                                tag -> attributes.add(new ImageAttribute(
                                        tag.getTagName(),
                                        tag.getDescription())
                                )
                        )
                );

        return attributes;
    }


    @Deprecated
    private Set<ImageAttribute> extractGeoLocation(final Metadata metadata){
        Set<ImageAttribute> attributes = new HashSet<>();
        metadata.getDirectoriesOfType(GpsDirectory.class)
                .forEach(directory -> directory.getTags()
                        .forEach(
                                tag -> attributes.add(new ImageAttribute(
                                                        tag.getTagName(),
                                                        tag.getDescription())
                                )
                        )
                );

        return attributes;
    }
}
