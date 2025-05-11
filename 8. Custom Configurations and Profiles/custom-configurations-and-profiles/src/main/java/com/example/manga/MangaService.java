package com.example.manga;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MangaService {

    private final MangaRepository mangaRepository;

    public MangaDto getPostById(Long id) {
        return MangaDto.fromManga(mangaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manga not found")));
    }

    public Page<MangaDto> getAllMangas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.desc("postedAt")) // Order by "createdAt" descending
        );
        return mangaRepository.findAll(pageable)
                .map(MangaDto::fromManga);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MangaDto createManga(String title, String imageUrl, String author, String description,
                                Integer publicationYear, Integer volumes, Integer chapters) {
        Manga manga = new Manga();
        manga.setTitle(title);
        manga.setImageUrl(imageUrl);
        manga.setDescription(description);
        manga.setAuthor(author);
        manga.setVolumes(volumes);
        manga.setChapters(chapters);
        manga.setPublicationYear(publicationYear);
        manga = mangaRepository.save(manga);
        return MangaDto.fromManga(manga);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Manga updateManga(Long mangaId, String title, String author, String description, Integer publicationYear) {
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new RuntimeException("Manga not found"));
        manga.setTitle(title);
        manga.setDescription(description);
        manga.setAuthor(author);
        manga.setPublicationYear(publicationYear);
        return mangaRepository.save(manga);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMangaById(Long mangaId) {
        mangaRepository.deleteById(mangaId);
    }

    public long numberOfMangas() {
        return mangaRepository.count();
    }
}
