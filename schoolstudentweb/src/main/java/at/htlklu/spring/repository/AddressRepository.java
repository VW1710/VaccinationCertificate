package at.htlklu.spring.repository;

import at.htlklu.spring.model.Absence;
import at.htlklu.spring.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> //dient für den Zugriff auf die Datenbank (Lehrerinnen)
{
  List<Address> findAll();

}

