

package com.smartshine.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;


    private LocalDate dueDate;

    private boolean completed;

    private String note;

    private LocalDate createdAt = LocalDate.now();

    // Геттеры и сеттеры

    private LocalDate date;

    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String employeeNote;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private AppUser assignedEmployee;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getEmployeeNote() {
        return employeeNote;
    }

    public void setEmployeeNote(String employeeNote) {
        this.employeeNote = employeeNote;
    }

    public AppUser getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(AppUser assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // Для обратной совместимости
    public AppUser getEmployee() {
        return this.assignedEmployee;
    }
}