package at.htlklu.spring.repository;

import at.htlklu.spring.model.Absence;
import at.htlklu.spring.model.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Integer> //dient für den Zugriff auf die Datenbank (Lehrerinnen)
{
  List<StudentSubject> findAll();

}

