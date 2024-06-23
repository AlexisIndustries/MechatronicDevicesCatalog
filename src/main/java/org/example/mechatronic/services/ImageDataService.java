package org.example.mechatronic.services;

import jakarta.transaction.Transactional;
import org.example.mechatronic.model.ImageData;
import org.example.mechatronic.model.ImageUploadResponse;
import org.example.mechatronic.model.MechatronicDevice;
import org.example.mechatronic.repositories.ImageDataRepository;
import org.example.mechatronic.repositories.MechatronicDeviceRepository;
import org.example.mechatronic.utilities.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageDataService {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private MechatronicDeviceRepository mechatronicDeviceRepository;

    public ImageUploadResponse uploadImage(MultipartFile file) throws IOException {

        imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

        return new ImageUploadResponse("Image uploaded successfully: " +
                file.getOriginalFilename());

    }

    @Transactional
    public ImageData getInfoByImageByName(String name) {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);

        return ImageData.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();

    }

    @Transactional
    public byte[] getImage(String name) {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);
        byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
        return image;
    }

    @Transactional
    public byte[] getImage(int id) {
        //Optional<ImageData> dbImage = imageDataRepository.findById((long) id);
        MechatronicDevice device = mechatronicDeviceRepository.findByDeviceId((long) id);
        byte[] image = ImageUtil.decompressImage(device.getImageData().getImageData());
        return image;
    }

}
