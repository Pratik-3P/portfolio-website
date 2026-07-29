package com.pratik.portfolio.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pratik.portfolio.model.Project;
import com.pratik.portfolio.repository.ProjectRepository;

/**
 * Runs once on startup. If the projects table is empty (e.g. first run
 * against a fresh database) it seeds it with the two projects from the
 * resume, so the site never looks empty. After that you can add / edit /
 * delete everything from /admin - this will not run again once rows exist.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final ProjectRepository projectRepository;

    public DataInitializer(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(String... args) {
        if (projectRepository.count() > 0) {
            return;
        }

        Project restaurant = new Project();
        restaurant.setTitle("Restaurant Management System");
        restaurant.setDescription("A role-based system for Admin, Staff, and Receptionist covering Customer, "
                + "Table, Billing, Payment, and Invoice management. Built on a relational schema with full CRUD "
                + "through Spring JDBC, following MVC architecture for maintainability.");
        restaurant.setTechStack("Java, Spring Core, Spring MVC, Spring JDBC, MySQL, JSP, Bootstrap");
        restaurant.setDisplayOrder(1);
        projectRepository.save(restaurant);

        Project hospital = new Project();
        hospital.setTitle("Hospital Management System");
        hospital.setDescription("Manages Patients, Doctors, Appointments, and Billing with secure CRUD "
                + "operations on a normalized relational database designed for data consistency.");
        hospital.setTechStack("Java, Servlets, JDBC, MySQL, HTML, CSS");
        hospital.setDisplayOrder(2);
        projectRepository.save(hospital);
    }
}
