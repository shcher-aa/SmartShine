package com.smartshine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smartshine.model.Task;
import com.smartshine.repository.TaskRepository;

@Controller
public class TaskAdminController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/admin/tasks")
    public String showTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
}
