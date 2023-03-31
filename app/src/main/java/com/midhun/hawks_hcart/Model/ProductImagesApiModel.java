package com.midhun.hawks_hcart.Model;

import com.midhun.hawks_hcart.View.Images;

import java.util.List;

public class ProductImagesApiModel {
   List<Images> images;

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public ProductImagesApiModel(List<Images> images) {
        this.images = images;
    }
}
