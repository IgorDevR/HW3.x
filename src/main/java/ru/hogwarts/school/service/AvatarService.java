package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(FacultyService.class);
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository,
                         RecordMapper recordMapper) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper;
    }

    public AvatarRecord create(long student_id, MultipartFile multipartFile) throws IOException {
        logger.info("{} method was called", "create");
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
        logger.info("{} method was called", "readFromFs");
        Avatar avatar = avatarRepository.findByStudentId(id).orElseThrow(() -> new NotFoundExceptionAvatar(id));
        return Pair.of(Files.readAllBytes(Paths.get(avatar.getFilePath())), avatar.getMediaType());
    }

    public Pair<byte[], String> readFromDb(long id) {
        logger.info("{} method was called", "readFromDb");
        Avatar avatar = avatarRepository.findByStudentId(id).orElseThrow(() -> new NotFoundExceptionAvatar(id));
        return Pair.of(avatar.getData(), avatar.getMediaType());
    }

    public Collection<AvatarRecord> readAllAvatarsPaging(int pageNum, int pageSize) {
        logger.info("{} method was called", "readAllAvatarsPaging");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());

    }

    public Collection<AvatarRecord> getAllAvatars() {
        logger.info("{} method was called", "getAllAvatars");
        return avatarRepository.findAll().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public AvatarRecord delete(Long idStudents) {
        logger.info("{} method was called", "delete");
        Avatar avatar = avatarRepository.findById(idStudents).orElseThrow(() -> new NotFoundExceptionFaculty(idStudents));
        avatarRepository.delete(avatar);
        return recordMapper.toRecord(avatar);
    }
}
