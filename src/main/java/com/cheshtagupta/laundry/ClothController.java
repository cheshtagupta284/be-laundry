package com.cheshtagupta.laundry;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("cloth")
public class ClothController {
    @Autowired
    private ClothRepository clothRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<Cloth> get(@RequestHeader("User") String email) {
        User user = userRepository.findByEmail(email);

        return clothRepository.findAllByUser(user);
    }

    @PostMapping("create")
    public Cloth create(@RequestHeader("User") String email, @RequestParam MultipartFile image, @RequestParam String type) throws IOException {

        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String random = UUID.randomUUID() + "." + extension;
        Files.write(Path.of("images", random), image.getBytes());

        User user = userRepository.findByEmail(email);
        Cloth cloth = new Cloth();
        cloth.setUser(user);
        cloth.setType(type);
        cloth.setImage(random);
        clothRepository.save(cloth);

        return cloth;
    }

    @GetMapping(value = "/image/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable String id) throws MalformedURLException {
        final Path root = Paths.get("images");
        Path file = root.resolve(id);
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }

    @PatchMapping("")
    public Cloth update(@RequestBody Cloth clothInput) {
        Cloth cloth = clothRepository.findById(clothInput.getId()).orElse(null);
        if (cloth == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "cloth not found"
            );
        }
        if (clothInput.getType() != null)
            cloth.setType(clothInput.getType());
        clothRepository.save(cloth);

        return cloth;
    }

    @DeleteMapping("")
    public void delete(@RequestBody Cloth clothInput) {
        Cloth cloth = clothRepository.findById(clothInput.getId()).orElse(null);
        if (cloth == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "cloth not found"
            );
        }
        clothRepository.delete(cloth);
    }
}
