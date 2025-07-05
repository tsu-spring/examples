package com.example.interactive_website.image;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Node;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * https://stackoverflow.com/questions/6495518/writing-image-metadata-in-java-preferably-png
 */
@Service
@RequiredArgsConstructor
public class ImageService {

    @Getter
    @Value("${image.root-folder}")
    private Path rootFolder;

    private final ImageRepository repository;

    @SneakyThrows
    @PostConstruct
    public void init() {
        // Create root folder if it doesn't exist
        Files.createDirectories(rootFolder);

        // Construct all the existing image entries in the root folder
        try (Stream<Path> stream = Files.walk(rootFolder)) {
            stream.filter(Files::isRegularFile).forEach(this::storeAlreadyExistingFilesInRepository);
        }
    }

    @SneakyThrows
    private void storeAlreadyExistingFilesInRepository(Path path) {
        Path relativePath = rootFolder.relativize(path);
        final var date = LocalDate.of(
                Integer.parseInt(relativePath.getName(0).toString()),
                Integer.parseInt(relativePath.getName(1).toString()),
                Integer.parseInt(relativePath.getName(2).toString())
        );
        final var info = readCustomData(Files.readAllBytes(path), "info");
        save(relativePath, date, info); // Save only entries (do not create files)
    }

    public List<Image> getImages() {
        return repository.getAll();
    }

    public List<Image> getImagesSortedByDate() {
        return repository.getAllSorted(Comparator.comparing(Image::getDate).reversed());
    }

    public void save(Path relativePath, LocalDate date, String info) {
        repository.save(new Image(relativePath, date, info));
    }

    @SneakyThrows
    public void save(MultipartFile file, LocalDate date, String info) {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        final String imageId = UUID.randomUUID().toString();

        // Construct a relative path for the image file
        Path relativePath = Path.of(Integer.toString(date.getYear()))
                .resolve(Integer.toString(date.getMonthValue()))
                .resolve(Integer.toString(date.getDayOfMonth()))
                .resolve(String.format("%s.png", imageId));

        // Resolve a full path of the image
        Path fullPath = getRootFolder().resolve(relativePath);

        // Create folders if they don't exist
        Files.createDirectories(fullPath.getParent());

        // Add "info" as PNG metadata entry inside image?
        if (info != null && !info.isBlank()) {
            bufferedImage = writeCustomData(bufferedImage, "info", info);
        }

        // Save image to file
        ImageIO.write(bufferedImage, "png", fullPath.toFile());

        // Save image entry
        save(relativePath, date, info);
    }

    public BufferedImage writeCustomData(BufferedImage buffImg, String key, String value) throws Exception {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();

        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);

        // Adding metadata
        IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);

        IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
        textEntry.setAttribute("keyword", key);
        textEntry.setAttribute("value", value);

        IIOMetadataNode text = new IIOMetadataNode("tEXt");
        text.appendChild(textEntry);

        IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
        root.appendChild(text);

        metadata.mergeTree("javax_imageio_png_1.0", root);

        // Writing the data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream stream = ImageIO.createImageOutputStream(baos);
        writer.setOutput(stream);
        writer.write(metadata, new IIOImage(buffImg, null, metadata), writeParam);
        stream.close();
        try (var byteArrayInputStream = new ByteArrayInputStream(baos.toByteArray())) {
            return ImageIO.read(byteArrayInputStream);
        }
    }

    public String readCustomData(byte[] imageData, String key) throws IOException {
        ImageReader imageReader = ImageIO.getImageReadersByFormatName("png").next();

        imageReader.setInput(ImageIO.createImageInputStream(new ByteArrayInputStream(imageData)), true);

        // read metadata of first image
        IIOMetadata metadata = imageReader.getImageMetadata(0);

        //this cast helps getting the contents
        List<Node> tEXtNodes = findNodesWithName("tEXtEntry",
                metadata.getAsTree(metadata.getNativeMetadataFormatName()));

        for (Node node : tEXtNodes) {
            String keyword = node.getAttributes().getNamedItem("keyword").getNodeValue();
            String value = node.getAttributes().getNamedItem("value").getNodeValue();
            if (key.equals(keyword)) {
                return value;
            }
        }
        return null;
    }

    private List<Node> findNodesWithName(String name, Node root) {
        List<Node> found = new ArrayList<>();
        Node n = root.getFirstChild();
        while (n != null) {
            if (n.getNodeName().equals(name)) {
                found.add(n);
            }
            found.addAll(findNodesWithName(name, n));
            n = n.getNextSibling();
        }
        return found;
    }
}
