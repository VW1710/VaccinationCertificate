package at.htlklu.spring.repository;

import at.htlklu.spring.model.Absence;
import at.htlklu.spring.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Integer> //dient für den Zugriff auf die Datenbank (Lehrerinnen)
{
  List<Absence> findAll();

}

