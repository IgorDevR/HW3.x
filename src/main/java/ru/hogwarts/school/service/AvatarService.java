package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.Exceptions.NotFoundExceptionAvatar;
import ru.hogwarts.school.Exceptions.NotFoundExceptionFaculty;
import ru.hogwarts.school.Exceptions.NotFoundExceptionStudent;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.AvatarRecord;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AvatarService {


    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final RecordMapper recordMapper;
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository,
                         RecordMapper recordMapper) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper;
    }

    //    public void create(long student_id, MultipartFile avatarFile) throws IOException {
//
//        Student student = studentRepository.findById(student_id).orElseThrow(() -> new NotFoundExceptionStudent());
//        Path filePath = Path.of(avatarsDir, student_id + "." + getExtensions(avatarFile.getOriginalFilename()));
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);
//
//        try (
//                InputStream is = avatarFile.getInputStream();
//                OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
//                BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//        ) {
//            bis.transferTo(bos);
//        }
//        Avatar avatar = avatarRepository.findByStudentId(student_id).orElse(new Avatar());
//        avatar.setStudent(student);
//        avatar.setFilePath(filePath.toString());
//        avatar.setFileSize(avatarFile.getSize());
//        avatar.setMediaType(avatarFile.getContentType());
//        avatar.setData(avatarFile.getBytes());
//        avatarRepository.save(avatar);
//
//    }
    public AvatarRecord create(long student_id, MultipartFile multipartFile) throws IOException {
        Student student = studentRepository.findById(student_id).orElseThrow(() -> new NotFoundExceptionStudent());
        byte[] data = multipartFile.getBytes();

        String extension = Optional.ofNullable(multipartFile.getOriginalFilename()).map(fileName ->
                fileName.substring(multipartFile.getOriginalFilename().lastIndexOf("."))).orElse("");
        Path path = Paths.get(avatarsDir).resolve((student_id + extension));
        Files.write(path, data);

        Avatar avatar = new Avatar();
        avatar.setData(data);
        avatar.setFileSize(data.length);
        avatar.setMediaType(multipartFile.getContentType());
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());

        return recordMapper.toRecord(avatarRepository.save(avatar));
    }

    public Pair<byte[], String> readFromFs(long id) throws IOException {
        Avatar avatar = avatarRepository.findByStudentId(id).orElseThrow(() -> new NotFoundExceptionAvatar(id));
        return Pair.of(Files.readAllBytes(Paths.get(avatar.getFilePath())), avatar.getMediaType());
    }

    public Pair<byte[], String> readFromDb(long id) {
        Avatar avatar = avatarRepository.findByStudentId(id).orElseThrow(() -> new NotFoundExceptionAvatar(id));
        return Pair.of(avatar.getData(), avatar.getMediaType());
    }

    public Collection<AvatarRecord> readAllAvatarsPaging(int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());

    }

    public Collection<AvatarRecord> getAllAvatars() {
        return avatarRepository.findAll().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public AvatarRecord delete(Long idStudents) {
        Avatar avatar = avatarRepository.findById(idStudents).orElseThrow(() -> new NotFoundExceptionFaculty(idStudents));
        avatarRepository.delete(avatar);
        return recordMapper.toRecord(avatar);
    }
}
