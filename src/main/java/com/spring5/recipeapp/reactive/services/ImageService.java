package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.domain.Recipe;
import com.spring5.recipeapp.reactive.repositories.IRecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageService implements IImageService {

    private final IRecipeRepository recipeRepository;

    public ImageService(IRecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(String recipeId, MultipartFile multipartFile) {
        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] bytesOfImage = new Byte[multipartFile.getBytes().length];
            int i =0;
            for(byte b: multipartFile.getBytes()){
                bytesOfImage[i++] = b;
            }
            recipe.setImage(bytesOfImage);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error when saving file", e);
            e.printStackTrace();
        }
    }


}
