package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;


    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @GetMapping("{facultyId}")
    public ResponseEntity getFaculty(@PathVariable Long facultyId) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity updateFaculty(@RequestBody Faculty faculty) {
        Faculty editFaculty = facultyService.updateFaculty(faculty);
        if (editFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editFaculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity deleteFaculty(@PathVariable Long facultyId) {
        Faculty editFaculty = facultyService.deleteFaculty(facultyId);
        if (editFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editFaculty);
    }

    @GetMapping("/filterColor/{color}")
    public ResponseEntity<List<Faculty>> filterStudentsByAge(@PathVariable String color) {

        List<Faculty> faculties = facultyService.getAllFaculty().stream().filter(a -> a.getColor().equals(color))
                .collect(Collectors.toList());
        if(faculties.size() == 0 || faculties.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }
}
