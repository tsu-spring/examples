package com.example.interactive_website.album;

import com.example.interactive_website.image.Image;
import com.example.interactive_website.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AlbumController {

    private final ImageService imageService;

    @GetMapping(value = {"/", "/album"})
    public String index(Model model) {
        // Create map of [Year]=>[Images] ordered by year descending
        Map<Integer, List<Image>> imagesByYears = new TreeMap<>(Comparator.reverseOrder());
        imagesByYears.putAll(imageService.getImagesSortedByDate().parallelStream()
                .collect(
                        Collectors.toMap(
                                i -> i.getDate().getYear(),
                                i -> new ArrayList<>(List.of(i)),
                                (a, b) -> {
                                    a.addAll(b);
                                    return a;
                                })
                )
        );
        model.addAttribute("imagesByYears", imagesByYears);
        return "index";
    }
}
