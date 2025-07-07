package com.smartshine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smartshine.model.Task;
import com.smartshine.repository.TaskRepository;

@Controller
@PreAuthorize("hasRole('MANAGER')")
public class ManagerTaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public ManagerTaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/manager/tasks")
    public String showTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
}
