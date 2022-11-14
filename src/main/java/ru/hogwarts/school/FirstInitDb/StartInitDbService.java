package ru.hogwarts.school.FirstInitDb;

import com.github.javafaker.Faker;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class StartInitDbService {


    private final FacultyService facultyService;
    private final StudentService studentService;
    private final AvatarService avatarService;
    private final AvatarRepository avatarRepository;
    private final RecordMapper recordMapper;
    private FacultyRecord facultyRecord;
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private final Faker faker = new Faker();

    public StartInitDbService(FacultyService facultyService, StudentService studentService, AvatarService avatarService, AvatarRepository avatarRepository, RecordMapper recordMapper) {
        this.facultyService = facultyService;
        this.studentService = studentService;
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
        this.recordMapper = recordMapper;
    }

    public void initDbStartValue() {


        FacultyRecord faculty1 = generateFaculty();
        FacultyRecord faculty2 = generateFaculty();
        FacultyRecord faculty3 = generateFaculty();

        faculty1 = facultyService.read(facultyService.create(faculty1).getId());
        faculty2 = facultyService.read(facultyService.create(faculty2).getId());
        faculty3 = facultyService.read(facultyService.create(faculty2).getId());


        List<StudentRecord> studentRecords = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (i <= 2) {
                studentRecords.add(studentService.read(studentService.create(generateStudent(faculty1)).getId()));
            } else if (i > 2 & i <= 5) {
                studentRecords.add(studentService.read(studentService.create(generateStudent(faculty2)).getId()));
            } else {
                studentRecords.add(studentService.read(studentService.create(generateStudent(faculty3)).getId()));
            }
            try {
                setAvatar(i + 1, studentRecords.get(i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private StudentRecord generateStudent(FacultyRecord facultyRecord) {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setName(faker.harryPotter().character());
        studentRecord.setAge(faker.random().nextInt(14, 25));
        if (facultyRecord != null) {
            studentRecord.setFaculty(facultyRecord);
        }
        return studentRecord;
    }

    private FacultyRecord generateFaculty() {
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setName(faker.harryPotter().house());
        facultyRecord.setColor(faker.color().name());
        return facultyRecord;
    }

    private void setAvatar(int numAvatar, StudentRecord studentRecord) throws IOException {

        Student student = recordMapper.toEntity(studentRecord);
        student.setId(studentRecord.getId());
        Path path = Path.of(avatarsDir + numAvatar + ".png");
        Pair<byte[], String> pair = Pair.of(Files.readAllBytes(path), MediaType.IMAGE_PNG_VALUE);

        Avatar avatar = new Avatar();
        avatar.setData(pair.getFirst());
        avatar.setFileSize(pair.getFirst().length);
        avatar.setMediaType(MediaType.IMAGE_PNG_VALUE);
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());

        avatarRepository.save(avatar);

    }

}
