package com.homeenv.service;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.junit.Test;


public class ImageMetadataServiceTest {
    @Test
    public void extractMetadata() throws Exception {
        ImageMetadataService imageMetadataService = new ImageMetadataService();
        ImmutableList.of(
        "/home/dominator/test/2.JPG",
        "/home/dominator/test/3.JPG",
        "/home/dominator/test/DSC_0002.JPG",
        "/home/dominator/test/icon.png",
        "/home/dominator/test/IMG_0080.JPG",
        "/home/dominator/test/IMGP9964.JPG"
        ).forEach(imageMetadataService::extractMetadata);
    }

}