package com.smartshine.controller;

import com.smartshine.model.Task;
import com.smartshine.model.AppUser;
import com.smartshine.repository.TaskRepository;
import com.smartshine.repository.AppUserRepository;
import com.smartshine.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/my")
    public List<Task> getMyTasks(Authentication auth) {
        AppUser user = userRepository.findByEmail(auth.getName()).orElseThrow();
        return taskRepository.findByAssignedEmployee(user);
    }

    @GetMapping("/new")
    public ModelAndView showTaskForm() {
        ModelAndView mav = new ModelAndView("create-task");
        mav.addObject("employees", userRepository.findByRole(AppUser.AppUserRole.EMPLOYEE));
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

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PostMapping
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

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("/manager-view")
    public String getTasksForManager(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Task> tasks;

        if (employeeId != null && clientId != null && date != null) {
            tasks = taskRepository.findByAssignedEmployeeIdAndClientIdAndDate(employeeId, clientId, date, pageable);
        } else if (employeeId != null && clientId != null) {
            tasks = taskRepository.findByAssignedEmployeeIdAndClientId(employeeId, clientId, pageable);
        } else if (employeeId != null && date != null) {
            tasks = taskRepository.findByAssignedEmployeeIdAndDate(employeeId, date, pageable);
        } else if (clientId != null && date != null) {
            tasks = taskRepository.findByClientIdAndDate(clientId, date, pageable);
        } else if (employeeId != null) {
            tasks = taskRepository.findByAssignedEmployeeId(employeeId, pageable);
        } else if (clientId != null) {
            tasks = taskRepository.findByClientId(clientId, pageable);
        } else if (date != null) {
            tasks = taskRepository.findByDate(date, pageable);
        } else {
            tasks = taskRepository.findAll(pageable);
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("employees", userRepository.findByRole(AppUser.AppUserRole.EMPLOYEE));
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("currentPage", page);

        return "manager-task-list";
    }
}