package com.smartshine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smartshine.model.AppUser;
import com.smartshine.model.Task;
import com.smartshine.repository.TaskRepository;

@Controller
public class EmployeeTaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/employee/tasks")
    public String showEmployeeTasks(@AuthenticationPrincipal AppUser currentUser, Model model) {
        List<Task> tasks = taskRepository.findByAssignedEmployee(currentUser);
        model.addAttribute("tasks", tasks);
        return "employee-tasks";
    }
}
