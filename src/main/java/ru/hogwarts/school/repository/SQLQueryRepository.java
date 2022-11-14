package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.SQLRequest.SQLQueryGetAvgAgeStudents;
import ru.hogwarts.school.SQLRequest.SQLQueryGetNumStudents;
import ru.hogwarts.school.entity.Student;

@Repository
public interface SQLQueryRepository extends JpaRepository<Student, Integer> {

    @Query(value = "SELECT COUNT (id) as NumStudents FROM student", nativeQuery = true)
    SQLQueryGetNumStudents getNumberOfStudents();

    @Query(value = "SELECT AVG (age) as AverageAgeStudents FROM student", nativeQuery = true)
    SQLQueryGetAvgAgeStudents getAverageAgeOfStudents();

}
