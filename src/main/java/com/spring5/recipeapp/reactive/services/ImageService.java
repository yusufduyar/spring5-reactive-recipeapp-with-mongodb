package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.domain.Recipe;
import com.spring5.recipeapp.reactive.repositories.reactive.IRecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageService implements IImageService {

    private final IRecipeReactiveRepository recipeReactiveRepository;

    public ImageService(IRecipeReactiveRepository IRecipeReactiveRepository) {
        this.recipeReactiveRepository = IRecipeReactiveRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile multipartFile) {
        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] byteObjects = new Byte[0];
                    try {
                        byteObjects = new Byte[multipartFile.getBytes().length];

                        int i = 0;

                        for (byte b : multipartFile.getBytes()) {
                            byteObjects[i++] = b;
                        }

                        recipe.setImage(byteObjects);

                        return recipe;

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

        recipeReactiveRepository.save(recipeMono.block()).block();

        return Mono.empty();

//        recipeReactiveRepository.findById(recipeId)
//                .map(recipe -> {
//                    Byte[] bytesOfImage = new Byte[0];
//                    try {
//                        bytesOfImage = new Byte[multipartFile.getBytes().length];
//
//                        int i =0;
//
//                        for(byte b: multipartFile.getBytes()){
//                            bytesOfImage[i++] = b;
//                        }
//
//                        recipe.setImage(bytesOfImage);
//
//                        return recipe;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        throw new RuntimeException(e);
//                    }
//                }).publish(recipeMono-> recipeReactiveRepository.save(recipeMono.block()));
//
//        return Mono.empty();
    }
}
