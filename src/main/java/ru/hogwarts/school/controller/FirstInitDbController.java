package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StartInitDb;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/firstInitDb")
public class FirstInitDbController {

    private final FacultyService facultyService;
    private final StudentService studentService;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final RecordMapper recordMapper;

    private final StartInitDb startInitDb;

    public FirstInitDbController(FacultyService facultyService, StudentService studentService, FacultyRepository facultyRepository, StudentRepository studentRepository, RecordMapper recordMapper, StartInitDb startInitDb) {
        this.facultyService = facultyService;
        this.studentService = studentService;
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper;
        this.startInitDb = startInitDb;
    }

    @PostMapping()
    public ResponseEntity<Collection<StudentRecord>> startInitDb() {


        Collection<StudentRecord> studentRecords = studentService.getAllStudent();
        studentRecords.stream().forEach(studentRecord ->studentService.delete(studentRecord.getId()));

        Collection<FacultyRecord> facultyRecords = facultyService.getAllFaculty();
        facultyRecords.stream().forEach(facultyRecord ->facultyService.deleteFaculty(facultyRecord.getId()));

//        facultyRepository.deleteAllInBatch();
//        studentRepository.deleteAllInBatch();

        startInitDb.initDbStartValue();

        facultyRecords = facultyService.getAllFaculty();
        studentRecords = studentService.getAllStudent();

        return ResponseEntity.ok(studentRecords);
    }
}
