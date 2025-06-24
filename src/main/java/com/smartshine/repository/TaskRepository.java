package com.smartshine.repository;

import com.smartshine.model.Task;
import com.smartshine.model.AppUser;
import com.smartshine.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedEmployee(AppUser employee);
    List<Task> findByDate(LocalDate date);
    List<Task> findByAssignedEmployeeAndDate(AppUser employee, LocalDate date);
    List<Task> findByAssignedEmployeeAndClientAndDate(AppUser employee, Client client, LocalDate date);
    List<Task> findByClient(Client client);
    List<Task> findByClientAndDate(Client client, LocalDate date);
}