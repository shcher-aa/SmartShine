package com.smartshine.repository;

import com.smartshine.model.Task;
import com.smartshine.model.AppUser;
import com.smartshine.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAssignedEmployee(AppUser employee, Pageable pageable);
    Page<Task> findByDate(LocalDate date, Pageable pageable);
    Page<Task> findByAssignedEmployeeAndDate(AppUser employee, LocalDate date, Pageable pageable);
    Page<Task> findByAssignedEmployeeAndClientAndDate(AppUser employee, Client client, LocalDate date, Pageable pageable);
    Page<Task> findByClient(Client client, Pageable pageable);
    Page<Task> findByClientAndDate(Client client, LocalDate date, Pageable pageable);
}