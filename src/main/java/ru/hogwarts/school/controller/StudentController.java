package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;
    private final RecordMapper recordMapper;

    public StudentController(StudentService service, RecordMapper recordMapper) {
        this.studentService = service;
        this.recordMapper = recordMapper;
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody @Valid StudentRecord studentRecord) {
        return ResponseEntity.ok(studentService.create(studentRecord));
    }

    @GetMapping("{id}")
    public ResponseEntity getStudent(@PathVariable long id) {
        return ResponseEntity.ok(studentService.read(id));
    }

    @PutMapping("{id}")
    public ResponseEntity updateStudent(@PathVariable long id, @RequestBody StudentRecord studentRecord) {
        return ResponseEntity.ok(studentService.update(id, studentRecord));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/getAll")
    public ResponseEntity<Collection<StudentRecord>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("/findByAge")
    public ResponseEntity<Collection<StudentRecord>> findByAge(@RequestParam int age) {
        return ResponseEntity.ok(studentService.findByAge(age));
    }

    //    @GetMapping("/findByBetweenAge")
    @GetMapping(params = {"min", "max"})
    public ResponseEntity<Collection<StudentRecord>> findAllStudentsBetweenAge(
            @RequestParam("min") int minAge,
            @RequestParam("max") int maxAge) {
        return ResponseEntity.ok(studentService.findByAgeBetween
                (minAge, maxAge));
    }

//    @GetMapping("/getFacultyStudentByIdStudent")
//    public ResponseEntity getFacultyStudentByIdStudent(@RequestParam long id) {
//        return ResponseEntity.ok(studentService.getFacultyStudentByIdStudent(id));
//    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity findFacultyByStudent(@PathVariable long id) {
        return ResponseEntity.ok(studentService.findFacultyByStudent(id));
    }

    @GetMapping("/getNumStudents")
    public ResponseEntity getNumStudents() {
        return ResponseEntity.ok(studentService.getNumStudents());
    }

    @GetMapping("/getAvgAgeStudents")
    public ResponseEntity getAvgAgeStudents() {
        return ResponseEntity.ok(studentService.getAvgAgeStudents());
    }

}
