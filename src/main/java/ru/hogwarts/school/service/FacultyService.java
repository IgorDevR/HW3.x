package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {

    private static long cntFaculty = 1l;
    private Map<Long, Faculty> facultyMap = new HashMap<>();

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(cntFaculty);
        facultyMap.put(cntFaculty++, faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        return facultyMap.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(Long id) {
        if (facultyMap.containsKey(id)) {
            return facultyMap.remove(id);
        }
        return null;
    }

    public List<Faculty> getAllFaculty() {
        return facultyMap.values().stream().toList();
    }


}
