package com.homeenv.util;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.bmp.BmpMetadataReader;
import com.drew.imaging.gif.GifMetadataReader;
import com.drew.imaging.ico.IcoMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.imaging.pcx.PcxMetadataReader;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.psd.PsdMetadataReader;
import com.drew.imaging.raf.RafMetadataReader;
import com.drew.imaging.tiff.TiffMetadataReader;
import com.drew.imaging.webp.WebpMetadataReader;
import com.drew.lang.RandomAccessStreamReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.adobe.AdobeJpegReader;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.file.FileMetadataReader;
import com.drew.metadata.icc.IccReader;
import com.drew.metadata.iptc.IptcReader;
import com.drew.metadata.jfif.JfifReader;
import com.drew.metadata.jfxx.JfxxReader;
import com.drew.metadata.jpeg.JpegCommentReader;
import com.drew.metadata.jpeg.JpegDhtReader;
import com.drew.metadata.jpeg.JpegDnlReader;
import com.drew.metadata.jpeg.JpegReader;
import com.drew.metadata.photoshop.DuckyReader;
import com.drew.metadata.photoshop.PhotoshopReader;
import com.drew.metadata.xmp.XmpReader;

import java.io.*;
import java.util.Arrays;

/**
 * Image metadata reader extracted from {@link com.drew.imaging.ImageMetadataReader} to avoid using XMP reader due to xmp core error.
 */
public class HomeenvImageMetadataReader {

    /**
     * Reads metadata from an {@link InputStream}.
     *
     * @param inputStream a stream from which the file data may be read.  The stream must be positioned at the
     *                    beginning of the file's data.
     * @return a populated {@link Metadata} object containing directories of tags with values and any processing errors.
     * @throws ImageProcessingException if the file type is unknown, or for general processing errors.
     */
    @NotNull
    public static Metadata readMetadata(@NotNull final InputStream inputStream) throws ImageProcessingException, IOException
    {
        return readMetadata(inputStream, -1);
    }

    /**
     * Reads metadata from an {@link InputStream} of known length.
     *
     * @param inputStream a stream from which the file data may be read.  The stream must be positioned at the
     *                    beginning of the file's data.
     * @param streamLength the length of the stream, if known, otherwise -1.
     * @return a populated {@link Metadata} object containing directories of tags with values and any processing errors.
     * @throws ImageProcessingException if the file type is unknown, or for general processing errors.
     */
    @NotNull
    public static Metadata readMetadata(@NotNull final InputStream inputStream, final long streamLength) throws ImageProcessingException, IOException
    {
        BufferedInputStream bufferedInputStream = inputStream instanceof BufferedInputStream
                ? (BufferedInputStream)inputStream
                : new BufferedInputStream(inputStream);

        FileType fileType = FileTypeDetector.detectFileType(bufferedInputStream);

        if (fileType == FileType.Jpeg){
            return JpegMetadataReader.readMetadata(bufferedInputStream, Arrays.asList(
                    new JpegReader(),
                    new JpegCommentReader(),
                    new JfifReader(),
                    new JfxxReader(),
                    new ExifReader(),
//                    new XmpReader(),
                    new IccReader(),
                    new PhotoshopReader(),
                    new DuckyReader(),
                    new IptcReader(),
                    new AdobeJpegReader(),
                    new JpegDhtReader(),
                    new JpegDnlReader()
            ));
        }


//
//        if (fileType == FileType.Tiff ||
//                fileType == FileType.Arw ||
//                fileType == FileType.Cr2 ||
//                fileType == FileType.Crw ||
//                fileType == FileType.Nef ||
//                fileType == FileType.Orf ||
//                fileType == FileType.Rw2)
//            return TiffMetadataReader.readMetadata(new RandomAccessStreamReader(bufferedInputStream, RandomAccessStreamReader.DEFAULT_CHUNK_LENGTH, streamLength));
//
//
//        if (fileType == FileType.Png)
//            return PngMetadataReader.readMetadata(bufferedInputStream);
//
//        if (fileType == FileType.Bmp)
//            return BmpMetadataReader.readMetadata(bufferedInputStream);
//
//        if (fileType == FileType.Gif)
//            return GifMetadataReader.readMetadata(bufferedInputStream);
//
//        if (fileType == FileType.Raf)
//            return RafMetadataReader.readMetadata(bufferedInputStream);

        throw new ImageProcessingException("File format is not supported");
    }

    /**
     * Reads {@link Metadata} from a {@link File} object.
     *
     * @param file a file from which the image data may be read.
     * @return a populated {@link Metadata} object containing directories of tags with values and any processing errors.
     * @throws ImageProcessingException for general processing errors.
     */
    @NotNull
    public static Metadata readMetadata(@NotNull final File file) throws ImageProcessingException, IOException
    {
        InputStream inputStream = new FileInputStream(file);
        Metadata metadata;
        try {
            metadata = readMetadata(inputStream, file.length());
        } finally {
            inputStream.close();
        }
        new FileMetadataReader().read(file, metadata);
        return metadata;
    }

    private HomeenvImageMetadataReader() throws Exception
    {
        throw new Exception("Not intended for instantiation");
    }
}
