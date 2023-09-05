package com.example.ticketKing.domain.photo.controller;

import com.example.ticketKing.domain.photo.entity.Photo;
import com.example.ticketKing.domain.photo.repository.PhotoRepository;
import com.example.ticketKing.global.config.AppConfig;
import com.example.ticketKing.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Transactional
public class PhotoController {
    private final PhotoRepository photoRepository;

    public RsData<Photo> parseFileInfo(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String creatDate = simpleDateFormat.format(new Date());

        String absolutePath = AppConfig.GET_FILE_DIR_PATH;
        String path = "/images/" + creatDate;

        if (!multipartFile.isEmpty()) {
            String contentType = multipartFile.getContentType();
            String originalFileExtension;

            //확장자명 없을 경우 잘못된 파일
            if (ObjectUtils.isEmpty(contentType)) {
                return RsData.of("F-1", "잘못된 파일입니다.", null);
            } else {
                if (contentType.contains("image/jpeg"))
                    originalFileExtension = ".jpg";
                else if (contentType.contains("image/png"))
                    originalFileExtension = ".png";
                else {
                    return RsData.of("F-2", "이미지 파일만 가능합니다.");
                }
            }

            String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
            Photo photo = Photo.builder()
                    .originalName(multipartFile.getOriginalFilename())
                    .filePath("/images/" + creatDate + "/" + newFileName)
                    .fileSize(multipartFile.getSize())
                    .build();

            photoRepository.save(photo);
            File file = new File(absolutePath + path + "/" + newFileName);

            if (!file.exists()) {
                file.mkdirs();
            }

            multipartFile.transferTo(file);

            return RsData.of("S-1", "이미지 파일을 등록하였습니다.", photo);
        }
        return RsData.of("F-3", "파일 등록을 실패했습니다.");
    }
}