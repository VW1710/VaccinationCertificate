package at.htlklu.spring.repository;

import at.htlklu.spring.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> //dient für den Zugriff auf die Datenbank (Lehrerinnen)
{
  List<Teacher> findByOrderBySurnameAscFirstnameAsc();

}

