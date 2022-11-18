SELECT student.name, student.age, faculty.name
from student
         left JOIN faculty on student.faculty_id = faculty.id;

SELECT student.name, student.age
from student
         INNER JOIN avatar on avatar.student_id = student.id;