package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.SQLRequest.SQLQueryGetAvgAgeStudents;
import ru.hogwarts.school.SQLRequest.SQLQueryGetNumStudents;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    Collection<Student> findByAge(int age);


    @Query(value = "SELECT COUNT (id) as NumStudents FROM student", nativeQuery = true)
    SQLQueryGetNumStudents getNumberOfStudents();

    @Query(value = "SELECT AVG (age) as AverageAgeStudents FROM student", nativeQuery = true)
    SQLQueryGetAvgAgeStudents getAverageAgeOfStudents();
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}
