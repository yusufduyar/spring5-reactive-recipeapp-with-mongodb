package com.spring5.recipeapp.reactive.controllers;

import com.spring5.recipeapp.reactive.commands.RecipeCommand;
import com.spring5.recipeapp.reactive.services.IImageService;
import com.spring5.recipeapp.reactive.services.IRecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class ImageController {
    private final IImageService imageService;
    private final IRecipeService recipeService;

    public ImageController(IImageService imageService, IRecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String getImageForm(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();
        model.addAttribute("recipe", recipeCommand);

        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(recipeId, file).block();

        return "redirect:/recipe/" + recipeId + "/show";
    }
//
//    @GetMapping("recipe/{recipeId}/recipeimage")
//    public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse httpServletResponse) throws IOException {
//        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();
//        byte[] recipeImage = new byte[recipeCommand.getImage().length];
//
//        int i = 0;
//        for (Byte wrappedByte : recipeCommand.getImage()) {
//            recipeImage[i++] = wrappedByte;
//        }
//
//        httpServletResponse.setContentType("image/jpeg");
//        InputStream stream = new ByteArrayInputStream(recipeImage);
//        IOUtils.copy(stream, httpServletResponse.getOutputStream());
//    }
}
