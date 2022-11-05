package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> filterFacultyByColor(String color) {

        return getAllFaculty().stream().filter(a -> a.getColor().equals(color))
                .collect(Collectors.toList());

    }
    public Faculty findFacultyByNameOrColor(String name, String color) {

        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }
    public Collection<Student> getStudentsOnFacultyByIdFaculty(long id) {
        return facultyRepository.findById(id).get().studentsOnFaculty();
//        return null;
    }

}
