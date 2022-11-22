package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService service) {
        this.studentService = service;
    }


    @GetMapping("{studentId}")
    public ResponseEntity getStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity updateStudent(@RequestBody Student student) {

        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("/filterAge/{age}")
    public ResponseEntity<Collection<Student>> filterStudentsForAge(@PathVariable Integer age) {

        Collection<Student> students = studentService.filterStudentsForAge(age);
        if (students.size() == 0 || students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/getAllStudentsBetweenAge")
    public ResponseEntity<Collection<Student>> getAllStudentsBetweenAge(
            @RequestParam int minAge, @RequestParam int maxAge) {
        return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/getFacultyStudentByIdStudent")
    public ResponseEntity getFacultyStudentByIdStudent(@RequestParam long id) {

        return ResponseEntity.ok(studentService.getFacultyStudentByIdStudent(id));
    }

}
