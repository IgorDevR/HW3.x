package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private static Long idStudent = 1l;
    private Map<Long, Student> studentMap = new HashMap<>();

    public Student createStudent(Student student) {
        studentMap.put(idStudent++, student);
        return student;
    }

    public Student getStudentById(Long id) {
        return studentMap.get(id);
    }
    public Student updateStudent(Student student){
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return student;
        }
        return null;
    }
    public Student deleteStudent(Long id){
        if (studentMap.containsKey(id)) {
            return studentMap.remove(id);
        }
        return null;
    }

    public List<Student> getAllStudent() {
        return studentMap.values().stream().toList();
    }
}
