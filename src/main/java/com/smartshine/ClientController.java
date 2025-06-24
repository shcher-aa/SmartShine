package com.smartshine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public String listClients(Model model) {
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/new")
    public String newClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form";
    }

    @GetMapping("/edit/{id}")
    public String editClientForm(@PathVariable Long id, Model model) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Клиент не найден: " + id));
        model.addAttribute("client", client);
        return "client-form";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute Client client) {
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }
}
