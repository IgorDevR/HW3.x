package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService service) {
        this.studentService = service;
    }


    @GetMapping("{studentId}")
    public ResponseEntity getStudent(@PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity updateStudent(@RequestBody Student student) {
        Student editStudent = studentService.updateStudent(student);
        if (editStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
        Student editStudent = studentService.deleteStudent(studentId);
        if (editStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @GetMapping("/filterAge/{age}")
    public ResponseEntity<List<Student>> filterStudentsForAge(@PathVariable Integer age) {

        List<Student>  students= studentService.getAllStudent().stream().filter(a -> a.getAge() == age)
                .collect(Collectors.toList());
        if(students.size() == 0 || students.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

}
