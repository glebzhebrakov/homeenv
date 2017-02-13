package com.homeenv.service;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.homeenv.config.ApplicationProperties;
import com.homeenv.domain.Image;
import com.homeenv.domain.ImageDuplicate;
import com.homeenv.messaging.IndexingRequest;
import com.homeenv.repository.ImageDuplicateRepository;
import com.homeenv.repository.ImageRepository;
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

    private final MessagingService messagingService;

    @Autowired
    public ImageMetadataService(ApplicationProperties applicationProperties,
                                ImageRepository imageRepository,
                                ImageDuplicateRepository imageDuplicateRepository,
                                MessagingService messagingService) {
        this.applicationProperties = applicationProperties;
        this.imageRepository = imageRepository;
        this.imageDuplicateRepository = imageDuplicateRepository;
        this.messagingService = messagingService;
    }

    public void indexStorage(){
        Collection<File> files = FileUtils.listFiles(new File(applicationProperties.getIndexing().getPath()),
                null,
                applicationProperties.getIndexing().getRecursive());

        Map<String, Image> indexedImages = new HashMap<>();
        files.forEach(file -> detectMimeType(file).ifPresent(mime -> {
          if (mime.startsWith("image")){

              calculateHash(file).ifPresent(hash -> {
                  Image maybeIndexedImage = imageRepository.findByHash(hash).orElse(null);
                  Set<ImageDuplicate> duplicates = new HashSet<>();
                    if (maybeIndexedImage == null){
                        maybeIndexedImage = new Image()
                                .withPath(file.getAbsolutePath())
                                .withHash(hash)
                                .withMime(mime)
                                .withIndexed(false);

                        log.info("#### Indexing : " + maybeIndexedImage.getPath());

                        messagingService.sendIndexingRequest(new IndexingRequest(
                                maybeIndexedImage.getPath(),
                                maybeIndexedImage.getHash()
                        ));
//                        extractMetadata(file);
                    } else {
                        log.info(String.format("### Found duplicates : \n original  %s , \n duplicate %s", maybeIndexedImage.getPath(), file.getAbsolutePath()));
                        duplicates.add(new ImageDuplicate(file.getAbsolutePath()));
                    }

                  saveImage(maybeIndexedImage, duplicates);

              });
          }


        }));
    }

    @Transactional
    private void saveImage(final Image image, final Set<ImageDuplicate> duplicates){
        try {
            Image img = imageRepository.save(image);
            if (duplicates != null){
                duplicates.forEach(imageDuplicate -> imageDuplicate.setImage(img));
                imageDuplicateRepository.save(duplicates);
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

    private Optional<String> detectMimeType(File file){
        try {
            return Optional.of(java.nio.file.Files.probeContentType(file.toPath()));
        } catch (Exception e) {
            log.error("unable to detect mime type of file " + file.getAbsolutePath(), e);
        }

        return Optional.empty();
    }

    private void extractMetadata(File file){
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
            if (gpsDirectories!= null){
                gpsDirectories.forEach(gpsDirectory -> {
                    GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                    if (geoLocation != null && !geoLocation.isZero()) {
                        System.out.println(geoLocation);
                    }
                });

            }
            metadata.getDirectories().forEach(directory -> directory.getTags().forEach(tag -> {
                System.out.println(tag);
            }));
        } catch (Throwable e) {
            log.error("unable extract metadata from file " + file.getAbsolutePath(), e);
        }
    }

    public void extractMetadata(String path)  {
        Metadata metadata = null;
        try {
            System.out.println("####################" + path);
            metadata = ImageMetadataReader.readMetadata(new File(path));
            metadata.getDirectories().forEach(directory -> directory.getTags().forEach(tag -> {
//                ExifDirectoryBase
                tag.getTagType();
            }));

            // obtain the Exif directory
            GpsDirectory directory
                    = metadata.getFirstDirectoryOfType(GpsDirectory.class);

// query the tag's value
//            System.out.println(directory.getInteger(ExifSubIFDDirectory.TAG_IMAGE_WIDTH));
//            System.out.println(directory.getInteger(ExifSubIFDDirectory.TAG_IMAGE_HEIGHT));
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO split to set of classes
    private void extractSize(Metadata metadata){

    }

    private void extractGeoLocation(Metadata metadata){

    }

    private void extractCameraModel(Metadata metadata){

    }
}
