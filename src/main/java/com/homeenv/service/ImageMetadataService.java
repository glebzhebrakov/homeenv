package com.homeenv.service;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ImageMetadataService {

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
            ExifSubIFDDirectory directory
                    = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

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
