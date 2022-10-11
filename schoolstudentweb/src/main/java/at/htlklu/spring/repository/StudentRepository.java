package at.htlklu.spring.repository;

import at.htlklu.spring.model.Student;
import at.htlklu.spring.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> //dient für den Zugriff auf die Datenbank (Lehrerinnen)
{
  List<Student> findByOrderBySurnameAscFirstnameAsc();

}

