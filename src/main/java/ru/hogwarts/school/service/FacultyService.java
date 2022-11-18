package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.NotFoundExceptionFaculty;
import ru.hogwarts.school.Exceptions.NothingFoundForQueryParameter;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    private Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord create(FacultyRecord facultyRecord) {
        logger.info("{} method was called", "create");
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord read(Long id) {
        logger.info("{} method was called", "read");
        return recordMapper.toRecord(facultyRepository.findById(id).orElseThrow(() -> new NotFoundExceptionFaculty(id)));

    }

    public FacultyRecord update(long id, FacultyRecord facultyRecord) {
        logger.info("{} method was called", "update");
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(() -> new NotFoundExceptionFaculty(id));
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        return recordMapper.toRecord(facultyRepository.save(oldFaculty));

    }

    public FacultyRecord deleteFaculty(Long id) {
        logger.info("{} method was called", "deleteFaculty");
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new NotFoundExceptionFaculty(id));
        facultyRepository.delete(faculty);
        return recordMapper.toRecord(faculty);
    }

    public Collection<FacultyRecord> getAllFaculty() {
        logger.info("{} method was called", "getAllFaculty");
        return facultyRepository.findAll().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<FacultyRecord> findByColor(String color) {
        logger.info("{} method was called", "findByColor");
        return facultyRepository.findAllByColor(color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());

    }

    public FacultyRecord findByNameOrColor(String name, String color) {
        logger.info("{} method was called", "findByNameOrColor");
        Faculty faculty = facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
        if (faculty == null) {
            throw new NothingFoundForQueryParameter();
        }
        return recordMapper.toRecord(faculty);
    }

    public Collection<StudentRecord> getStudentsOnFacultyByIdFaculty(long id) {
        logger.info("{} method was called", "getStudentsOnFacultyByIdFaculty");
        Collection<Student> students = recordMapper.toEntity(read(id)).studentsOnFaculty();
        if (students.size() == 0 || students.isEmpty()) {
            throw new NothingFoundForQueryParameter();
        }
        return students.stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findStudentsByFaculty(long id) {
        logger.info("{} method was called", "getStudentsOnFacultyByIdFaculty");
        return facultyRepository.findById(id).orElseThrow(() -> new NotFoundExceptionFaculty(id)).getStudents()
                .stream().map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public String getLongestNameFaculty() {
        logger.info("{} method was called", "getLongestNameFaculty");
        return facultyRepository.findAll().stream()
                .map(faculty -> faculty.getName())
                .max(String::compareTo)
                .orElse(null);
    }
}
