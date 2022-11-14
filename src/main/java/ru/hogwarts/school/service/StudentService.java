package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.NotFoundExceptionStudent;
import ru.hogwarts.school.Exceptions.NothingFoundForQueryParameter;
import ru.hogwarts.school.SQLRequest.SQLQueryGetAvgAgeStudents;
import ru.hogwarts.school.SQLRequest.SQLQueryGetNumStudents;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.SQLQueryRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SQLQueryRepository sqlQueryRepository;
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public StudentService(StudentRepository studentRepository,
                          SQLQueryRepository sqlQueryRepository, FacultyRepository facultyRepository,
                          RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.sqlQueryRepository = sqlQueryRepository;
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public StudentRecord create(StudentRecord studentRecord) {
        Student student = recordMapper.toEntity(studentRecord);
        Faculty faculty = Optional.ofNullable(studentRecord.getFaculty())
                .map(FacultyRecord::getId)
                .flatMap(facultyRepository::findById)
                .orElse(null);
        student.setFaculty(faculty);
        return recordMapper.toRecord(studentRepository.save(student));
    }

    public StudentRecord read(long id) {
        return recordMapper.toRecord(studentRepository.findById(id).orElseThrow(() -> new NotFoundExceptionStudent(id)));
    }

    public StudentRecord update(long id ,StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new NotFoundExceptionStudent(id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
        oldStudent.setFaculty(Optional.ofNullable(studentRecord.getFaculty())
                .map(FacultyRecord::getId)
                .flatMap(facultyRepository::findById)
                .orElse(null));
        return recordMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord delete(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundExceptionStudent(id));
        studentRepository.delete(student);
        return recordMapper.toRecord(student);
    }

    public Collection<StudentRecord> getAllStudent() {
        return studentRepository.findAll().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAge(int age) {
        return studentRepository.findByAge(age).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAgeBetween(int minAge, int maxAge) {
        Collection<StudentRecord> students = studentRepository.findByAgeBetween(minAge, maxAge)
                .stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());

        if (students.size() == 0 || students.isEmpty()) {
            throw new NothingFoundForQueryParameter();
        }
        return students;
    }

//    public FacultyRecord getFacultyStudentByIdStudent(long id) {
//       Faculty faculty = recordMapper.toEntity(read(id)).facultyStudent();
//        if (faculty == null) {
//            throw new NothingFoundForQueryParameter();
//        }
//        return recordMapper.toRecord(faculty);
//    }
    public FacultyRecord findFacultyByStudent(long id) {
        return read(id).getFaculty();
    }

    public SQLQueryGetNumStudents getNumStudents() {
        return sqlQueryRepository.getNumberOfStudents();
    }

    public SQLQueryGetAvgAgeStudents getAvgAgeStudents() {
        return sqlQueryRepository.getAverageAgeOfStudents();
    }
}
