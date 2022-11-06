package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceprions.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {

private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return  studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
       return studentRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }
    public Student updateStudent(Student student){

        return studentRepository.save(student);
    }
    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Collection<Student> filterStudentsForAge(Integer age) {

        return getAllStudent().stream().filter(a -> a.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        return  studentRepository.findByAgeBetween(minAge, maxAge);
    }
    public Faculty getFacultyStudentByIdStudent(long id) {
        return  getStudentById(id).facultyStudent();
//        return  null;
    }

}
