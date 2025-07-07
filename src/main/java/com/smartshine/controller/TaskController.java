package com.smartshine.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.smartshine.model.Role;
import com.smartshine.model.Task;
import com.smartshine.repository.AppUserRepository;
import com.smartshine.repository.ClientRepository;
import com.smartshine.repository.TaskRepository;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/new")
    public ModelAndView showTaskForm() {
        ModelAndView mav = new ModelAndView("create-task");
        mav.addObject("employees", userRepository.findByRole(Role.EMPLOYEE));
        mav.addObject("clients", clientRepository.findAll());
        return mav;
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PostMapping("/create")
    public String createTaskFromForm(@RequestParam String description,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime timeStart,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime timeEnd,
                                     @RequestParam Long employeeId,
                                     @RequestParam Long clientId) {

        Task task = new Task();
        task.setDescription(description);
        task.setDate(date);
        task.setTimeStart(timeStart);
        task.setTimeEnd(timeEnd);
        task.setAssignedEmployee(userRepository.findById(employeeId).orElse(null));
        task.setClient(clientRepository.findById(clientId).orElse(null));
        task.setCompleted(false);
        task.setEmployeeNote("");
        taskRepository.save(task);

        return "redirect:/manager/tasks";
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PostMapping("/json")
    public String createTaskFromJson(@RequestBody Task task) {
        task.setCompleted(false);
        task.setEmployeeNote("");
        taskRepository.save(task);
        return "Задача успешно создана!";
    }

    @PostMapping("/form")
    public Task createTask(@RequestBody Task task) {
        task.setCompleted(false);
        task.setEmployeeNote("");
        return taskRepository.save(task);
    }

    @PutMapping("/{id}/complete")
    public Task markComplete(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        Task task = taskRepository.findById(id).orElseThrow();
        String note = payload.getOrDefault("note", "");
        task.setCompleted(true);
        task.setEmployeeNote(note);
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}