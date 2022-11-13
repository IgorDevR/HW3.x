package ru.hogwarts.school.service;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

@Service
public class StartInitDb {


    private final FacultyService facultyService;
    private final StudentService studentService;
    private final AvatarService avatarService;
    private FacultyRecord facultyRecord;
    private final RecordMapper recordMapper;
    private final Faker faker = new Faker();

    public StartInitDb(FacultyService facultyService, StudentService studentService, AvatarService avatarService, RecordMapper recordMapper) {
        this.facultyService = facultyService;
        this.studentService = studentService;
        this.avatarService = avatarService;
        this.recordMapper = recordMapper;
    }

    public void initDbStartValue() {


//        Faculty faculty1 = recordMapper.toEntity(generateFaculty());
//        Faculty faculty2 = recordMapper.toEntity(generateFaculty());
//        Faculty faculty3 = recordMapper.toEntity(generateFaculty());

        FacultyRecord faculty1 = generateFaculty();
        FacultyRecord faculty2 = generateFaculty();
        FacultyRecord faculty3 = generateFaculty();

        faculty1 = facultyService.read(facultyService.create(faculty1).getId());
        faculty2 = facultyService.read(facultyService.create(faculty2).getId());
        faculty3 = facultyService.read(facultyService.create(faculty2).getId());

        StudentRecord student1Faculty1 = generateStudent(faculty1);
        StudentRecord student2Faculty1 = generateStudent(faculty1);
        StudentRecord student3Faculty1 = generateStudent(faculty1);

        studentService.create(student1Faculty1);
        studentService.create(student2Faculty1);
        studentService.create(student3Faculty1);

        StudentRecord student1Faculty2 = generateStudent(faculty2);
        StudentRecord student2Faculty2 = generateStudent(faculty2);
        StudentRecord student3Faculty2 = generateStudent(faculty2);

        studentService.create(student1Faculty2);
        studentService.create(student2Faculty2);
        studentService.create(student3Faculty2);

        StudentRecord student1Faculty3 = generateStudent(faculty3);
        StudentRecord student2Faculty3 = generateStudent(faculty3);
        StudentRecord student3Faculty3 = generateStudent(faculty3);

        studentService.create(student1Faculty3);
        studentService.create(student2Faculty3);
        studentService.create(student3Faculty3);


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

}
