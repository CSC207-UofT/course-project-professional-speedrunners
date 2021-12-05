package com.boba.bobabuddy.core.service.firebaseImage;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public interface IImageService {

    void init(ApplicationReadyEvent event) throws IOException;

    String save(String imageUrl, String originalImageName) throws IOException;

    String getImageUrl(String fileName);

    default String generateFileName(String originalFilename) {
        return UUID.randomUUID() + "." + getExtension(originalFilename);
    }

    default String getExtension(String originalFilename) {
        return StringUtils.getFilenameExtension(originalFilename);
    }

    default BufferedImage convertUrlToImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        return ImageIO.read(url);
    }

    default byte[] getByteArrays(BufferedImage bufferedImage, String format) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, format, baos);
            baos.flush();
            return baos.toByteArray();
        }
    }


}
