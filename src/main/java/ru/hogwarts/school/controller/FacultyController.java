package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.FacultyService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;
    private final RecordMapper recordMapper;

    public FacultyController(FacultyService facultyService, RecordMapper recordMapper) {
        this.facultyService = facultyService;
        this.recordMapper = recordMapper;
    }

    @PostMapping
    public ResponseEntity createFaculty(@RequestBody @Valid FacultyRecord facultyRecord) {
        return ResponseEntity.ok(facultyService.create(facultyRecord));
    }

    @GetMapping("{Id}")
    public ResponseEntity getFaculty(@PathVariable long Id) {
        return ResponseEntity.ok(facultyService.read(Id));
    }

    @PutMapping("{id}")
    public ResponseEntity updateFaculty(@PathVariable long id,
                                        @RequestBody @Valid FacultyRecord facultyRecord) {
        return ResponseEntity.ok(facultyService.update(id, facultyRecord));
    }

    @DeleteMapping("{Id}")
    public ResponseEntity deleteFaculty(@PathVariable long Id) {
        facultyService.deleteFaculty(Id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<Collection<FacultyRecord>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());

    }

    @GetMapping("/findByColor")
    public ResponseEntity<Collection<FacultyRecord>> findByColor(@RequestParam String color) {
        return ResponseEntity.ok(facultyService.findByColor(color));
    }

    @GetMapping("/findByNameOrColor")
    public ResponseEntity findByNameOrColor(@RequestParam String nameOrColor) {
        return ResponseEntity.ok(facultyService.findByNameOrColor(nameOrColor, nameOrColor));
    }

//    @GetMapping("/getStudentsOnFacultyByIdFaculty")
//    public ResponseEntity<Collection<StudentRecord>> getStudentsOnFacultyByIdFaculty(@RequestParam long id) {
//        return ResponseEntity.ok(facultyService.getStudentsOnFacultyByIdFaculty(id));
//    }

    @GetMapping("/{id}/student")
    public ResponseEntity<Collection<StudentRecord>> findStudentsByFaculty(@PathVariable long id){
        return ResponseEntity.ok(facultyService.findStudentsByFaculty(id));
    }

    @GetMapping("/getLongestNameFaculty")
    public ResponseEntity<String> getLongestNameFaculty(){
        return ResponseEntity.ok(facultyService.getLongestNameFaculty());
    }

}
