package ru.hogwarts.school.controller;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.record.AvatarRecord;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

        @PostMapping(value = "{id}/uploadAvatar/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity create( @RequestParam MultipartFile avatar, @PathVariable long id) throws IOException {
        avatarService.create(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/from-fs")
    public ResponseEntity<byte[]> readFromFs(@PathVariable long id) throws IOException {
        Pair<byte[], String> pair = avatarService.readFromFs(id);
        return read((pair));
    }

    @GetMapping("/{id}/from-db")
    public ResponseEntity<byte[]> readFromDb(@PathVariable long id) throws IOException {
        Pair<byte[], String> pair = avatarService.readFromDb(id);
        return read((pair));

    }

    private ResponseEntity<byte[]> read(Pair<byte[], String> pair) {
        return ResponseEntity.ok()
                .contentLength(pair.getFirst().length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(pair.getFirst());
    }

    @GetMapping("/getAllAvatarsPaging")
    public ResponseEntity<Collection<AvatarRecord>> readAllAvatarsPaging(@RequestParam(value = "namberPage")int pageNum,@RequestParam(value = "quantityPerPage") int pageSize) throws IOException {
        return ResponseEntity.ok(avatarService.readAllAvatarsPaging(pageNum, pageSize));
    }



//    @GetMapping("/fromDB/{id}")
//    public ResponseEntity<byte[]> getFromDB(@PathVariable long id) {
//
//        Avatar avatar = avatarService.findAvatar(id);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
//        headers.setContentLength(avatar.getData().length);
//        return ResponseEntity.status(HttpStatus.OK).headers(headers)
//                .body(avatar.getData());
//    }
//
//    @GetMapping("/fromFile/{id}")
//    public void getFromFile(@PathVariable long id, HttpServletResponse response) throws IOException {
//
//        Avatar avatar = avatarService.findAvatar(id);
//        Path path = Path.of(avatar.getFilePath());
//        try (
//                InputStream is = Files.newInputStream(path);
//                OutputStream os = response.getOutputStream();
//        ) {
//            response.setContentType(avatar.getMediaType());
//            response.setContentLength((int) avatar.getFileSize());
//            is.transferTo(os);
//            response.setStatus(200);
//        }
//    }
}
