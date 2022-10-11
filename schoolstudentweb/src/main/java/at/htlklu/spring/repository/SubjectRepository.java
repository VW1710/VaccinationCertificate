package at.htlklu.spring.repository;

import at.htlklu.spring.model.Absence;
import at.htlklu.spring.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> //dient für den Zugriff auf die Datenbank (Lehrerinnen)
{
  List<Subject> findAll();

}

