package at.htlklu.spring;


import at.htlklu.spring.controller.SchoolClassController;
import at.htlklu.spring.model.Absence;
import at.htlklu.spring.model.SchoolClass;
import at.htlklu.spring.model.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Month;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaAuditing
public class Application
{
	public static void main(String[] args)
	{

		SpringApplication.run(Application.class, args);


	}
}
