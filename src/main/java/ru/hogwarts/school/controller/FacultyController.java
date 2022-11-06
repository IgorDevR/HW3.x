package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;


    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @GetMapping("{facultyId}")
    public ResponseEntity getFaculty(@PathVariable Long facultyId) {

        return ResponseEntity.ok(facultyService.getFacultyById(facultyId));
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity updateFaculty(@RequestBody Faculty faculty) {

        return ResponseEntity.ok(facultyService.updateFaculty(faculty));
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity deleteFaculty(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        var q = facultyService.getAllFaculty();
        return ResponseEntity.ok(facultyService.getAllFaculty());

    }

    @GetMapping("/filterColor/{color}")
    public ResponseEntity<Collection<Faculty>> filterStudentsByAge(@PathVariable String color) {

        Collection<Faculty> faculties = facultyService.filterFacultyByColor(color);
        if(faculties.size() == 0 || faculties.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/findByNameOrColor")
    public ResponseEntity findFacultyByNameOrColor(@RequestParam String nameOrColor) {

        Faculty faculty = facultyService.findFacultyByNameOrColor(nameOrColor, nameOrColor);
        if(faculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/getStudentsOnFacultyByIdFaculty")
    public ResponseEntity<Collection<Student>> getStudentsOnFacultyByIdFaculty(@RequestParam long id) {
        Collection<Student> students = facultyService.getStudentsOnFacultyByIdFaculty(id);
        if(students.size() == 0 || students.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

}
