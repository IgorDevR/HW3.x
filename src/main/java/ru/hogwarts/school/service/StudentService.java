package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;
    private Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository,
                          RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public StudentRecord create(StudentRecord studentRecord) {
        logger.info("{} method was called", "create");
        Student student = recordMapper.toEntity(studentRecord);
        Faculty faculty = Optional.ofNullable(studentRecord.getFaculty())
                .map(FacultyRecord::getId)
                .flatMap(facultyRepository::findById)
                .orElse(null);
        student.setFaculty(faculty);
        return recordMapper.toRecord(studentRepository.save(student));
    }

    public StudentRecord read(long id) {
        logger.info("{} method was called", "read");
        return recordMapper.toRecord(studentRepository.findById(id).orElseThrow(() -> new NotFoundExceptionStudent(id)));
    }

    public StudentRecord update(long id, StudentRecord studentRecord) {
        logger.info("{} method was called", "update");
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
        logger.info("{} method was called", "delete");
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundExceptionStudent(id));
        studentRepository.delete(student);
        return recordMapper.toRecord(student);
    }

    public Collection<StudentRecord> getAllStudent() {
        logger.info("{} method was called", "getAllStudent");
        return studentRepository.findAll().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAge(int age) {
        logger.info("{} method was called", "findByAge");
        return studentRepository.findByAge(age).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAgeBetween(int minAge, int maxAge) {
        logger.info("{} method was called", "findByAgeBetween");
        Collection<StudentRecord> students = studentRepository.findByAgeBetween(minAge, maxAge)
                .stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());

        if (students.size() == 0 || students.isEmpty()) {
            throw new NothingFoundForQueryParameter();
        }
        return students;
    }

    public FacultyRecord findFacultyByStudent(long id) {
        logger.info("{} method was called", "findFacultyByStudent");
        return read(id).getFaculty();
    }

    public SQLQueryGetNumStudents getNumStudents() {
        logger.info("{} method was called", "getNumStudents");
        return studentRepository.getNumberOfStudents();
    }

    public SQLQueryGetAvgAgeStudents getAvgAgeStudents() {
        logger.info("{} method was called", "getAvgAgeStudents");
        return studentRepository.getAverageAgeOfStudents();
    }

    public Collection<StudentRecord> getLastFiveStudents() {
        logger.info("{} method was called", "getLastFiveStudents");
        return studentRepository.getLastFiveStudents()
                .stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<String> getStudentsByFirstLetter(String firstLetterName) {
        logger.info("{} method was called", "getStudentsByFirstLetter");
        return studentRepository.findAll()
                .stream()
                .map(student -> student.getName())
                .filter(name -> name.startsWith(firstLetterName))
                .map(name -> name.toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAvgAgeAllStudents() {
        logger.info("{} method was called", "getAvgAgeAllStudents");
        return studentRepository.findAll()
                .stream()
                .mapToDouble(a -> a.getAge())
                .average().orElse(-1);

    }

    public void getAllStudentsMultiThreadPrintln() {
        logger.info("{} method was called", "getAllStudentsMultiThread");
        List<Student> studentRecords = studentRepository.findAll()
                .stream()
                .collect(Collectors.toList());


        System.out.println("Thread name: " + Thread.currentThread().getName() + " " + studentRecords.get(0) + "number = " + 0);
        System.out.println("Thread name: " + Thread.currentThread().getName() + " " + studentRecords.get(1) + "number = " + 1);

        new Thread(() -> {
            System.out.println("Thread name: " + Thread.currentThread().getName() + " " + studentRecords.get(2) + "number = " + 2);
            System.out.println("Thread name: " + Thread.currentThread().getName() + " " + studentRecords.get(3) + "number = " + 3);
        }).start();

        System.out.println("Thread name: " + Thread.currentThread().getName() + " " + studentRecords.get(4) + "number = " + 4);
        System.out.println("Thread name: " + Thread.currentThread().getName() + " " + studentRecords.get(5) + "number = " + 5);

        new Thread(() -> {
            System.out.println("Thread name: " + Thread.currentThread().getName() + " " + studentRecords.get(6) + "number = " + 6);
            System.out.println("Thread name: " + Thread.currentThread().getName() + " " + studentRecords.get(7) + "number = " + 7);
        }).start();

        System.out.println("Thread name: " + Thread.currentThread().getName() + " " + studentRecords.get(8) + "number = " + 8);

    }

    public void getAllStudentsMultiThreadPrintlnSync() {
        logger.info("{} method was called", "getAllStudentsMultiThread");
        List<Student> students = studentRepository.findAll()
                .stream()
                .collect(Collectors.toList());

        System.out.println("collection:");
        students.forEach(System.out::println);

        multiThreadPrintlnSync(students);
        multiThreadPrintlnSync(students);

        new Thread(() -> {
            multiThreadPrintlnSync(students);
            multiThreadPrintlnSync(students);
        }).start();
//
        multiThreadPrintlnSync(students);
        multiThreadPrintlnSync(students);
//
        new Thread(() -> {
            multiThreadPrintlnSync(students);
            multiThreadPrintlnSync(students);
        }).start();

        multiThreadPrintlnSync(students);
    }

    Integer index = 0;

    private void multiThreadPrintlnSync(List<Student> students) {

        synchronized (index) {
        System.out.println(students.get(index).getName());
            index++;
            if (index == students.size()) {
                index = 0;
            }
        }
    }



}
