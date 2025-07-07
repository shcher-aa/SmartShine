package com.smartshine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartshine.model.Client;
import com.smartshine.repository.ClientRepository;

@Controller
@RequestMapping("/employee/clients")
public class EmployeeClientController {

    private final ClientRepository clientRepository;

    @Autowired
    public EmployeeClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public String showClients(Model model) {
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }
}
