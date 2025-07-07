

package com.smartshine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smartshine.model.Client;
import com.smartshine.repository.ClientRepository;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/admin/clients")
    public String showClients(Model model) {
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }
}