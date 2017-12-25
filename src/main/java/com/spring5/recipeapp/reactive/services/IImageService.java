package com.spring5.recipeapp.reactive.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface IImageService {
    Mono<Void> saveImageFile(String recipeId, MultipartFile multipartFile);
}
