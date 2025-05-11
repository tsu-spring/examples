package com.example.jikan.moe;

import com.example.manga.MangaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("dev")
@Service
@RequiredArgsConstructor
public class JikanMoeService {

    private final JikanMoeProperties jikanMoeProperties;
    private final RestTemplate restTemplate;

    public List<MangaDto> topManga(int page) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("page", page);

        ResponseEntity<Map> response = restTemplate.getForEntity(jikanMoeProperties.getTopMangaEndpoint(),
                Map.class, uriVariables);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Could not get top manga from JikanMoe");
        }
        List<Map<String, Object>> mangaList = (List<Map<String, Object>>) response.getBody().get("data");
        List<MangaDto> mangas = new ArrayList<>();

        for (Map<String, Object> manga : mangaList) {
            MangaDto mangaDto = new MangaDto();
            mangaDto.setTitle((String) manga.get("title"));

            // Extracting author information from 'authors' list
            List<Map<String, Object>> authors = (List<Map<String, Object>>) manga.get("authors");
            String author = (authors != null && !authors.isEmpty()) ? (String) authors.get(0).get("name") : "Unknown";
            mangaDto.setAuthor(author);

            // Extracting publication year from 'published' -> 'from' date
            Map<String, Object> published = (Map<String, Object>) manga.get("published");
            String fromDate = (published != null) ? (String) published.get("from") : null;
            Integer publicationYear = (fromDate != null && fromDate.length() >= 4) ? Integer.parseInt(fromDate.substring(0, 4)) : 0;
            mangaDto.setPublicationYear(publicationYear);

            // Extracting image URL from 'images' -> 'jpg' -> 'image_url'
            Map<String, Object> images = (Map<String, Object>) manga.get("images");
            if (images != null) {
                Map<String, Object> jpg = (Map<String, Object>) images.get("jpg");
                if (jpg != null) {
                    String imageUrl = (String) jpg.get("image_url");
                    mangaDto.setImageUrl(imageUrl);
                }
            }

            mangaDto.setDescription((String) manga.get("synopsis"));

            // Extracting volumes and chapters
            Integer volumes = (manga.get("volumes") instanceof Integer) ? (Integer) manga.get("volumes") : null;
            Integer chapters = (manga.get("chapters") instanceof Integer) ? (Integer) manga.get("chapters") : null;
            mangaDto.setVolumes(volumes);
            mangaDto.setChapters(chapters);

            mangaDto.setPostedAt(LocalDateTime.now());
            mangaDto.setUpdatedAt(LocalDateTime.now());

            mangas.add(mangaDto);
        }
        return mangas;
    }
}
