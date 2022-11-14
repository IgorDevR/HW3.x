package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.NotFoundExceptionFaculty;
import ru.hogwarts.school.Exceptions.NothingFoundForQueryParameter;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.SQLQueryRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final SQLQueryRepository sqlQueryRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository, SQLQueryRepository sqlQueryRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.sqlQueryRepository = sqlQueryRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord create(FacultyRecord facultyRecord) {
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord read(Long id) {
        return recordMapper.toRecord(facultyRepository.findById(id).orElseThrow(() -> new NotFoundExceptionFaculty(id)));

    }

    public FacultyRecord update(long id, FacultyRecord facultyRecord) {
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(() -> new NotFoundExceptionFaculty(id));
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        return recordMapper.toRecord(facultyRepository.save(oldFaculty));

    }

    public FacultyRecord deleteFaculty(Long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new NotFoundExceptionFaculty(id));
        facultyRepository.delete(faculty);
        return recordMapper.toRecord(faculty);
    }

    public Collection<FacultyRecord> getAllFaculty() {
        return facultyRepository.findAll().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<FacultyRecord> findByColor(String color) {
        return facultyRepository.findAllByColor(color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());

    }

    public FacultyRecord findByNameOrColor(String name, String color) {
        Faculty faculty = facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
        if (faculty == null) {
            throw new NothingFoundForQueryParameter();
        }
        return recordMapper.toRecord(faculty);
    }

    public Collection<StudentRecord> getStudentsOnFacultyByIdFaculty(long id) {
        Collection<Student> students = recordMapper.toEntity(read(id)).studentsOnFaculty();
        if (students.size() == 0 || students.isEmpty()) {
            throw new NothingFoundForQueryParameter();
        }
        return students.stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findStudentsByFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new NotFoundExceptionFaculty(id)).getStudents()
                .stream().map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

}
