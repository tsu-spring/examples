package com.example.interactive_website.image;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class ImageRepository {

    private final List<Image> images = new ArrayList<>();

    public List<Image> getAll() {
        return new ArrayList<>(images);
    }

    public List<Image> getAllSorted(Comparator<Image> comparator) {
        List<Image> images = getAll();
        images.sort(comparator);
        return images;
    }

    public void save(Image image) {
        images.add(image);
    }
}
