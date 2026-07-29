package com.pratik.portfolio.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pratik.portfolio.model.ContactMessage;
import com.pratik.portfolio.model.Project;
import com.pratik.portfolio.repository.ContactMessageRepository;
import com.pratik.portfolio.repository.ProjectRepository;

@Controller
public class PortfolioController {

    private final ProjectRepository projectRepository;
    private final ContactMessageRepository contactMessageRepository;

    public PortfolioController(ProjectRepository projectRepository,
                                ContactMessageRepository contactMessageRepository) {
        this.projectRepository = projectRepository;
        this.contactMessageRepository = contactMessageRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Project> projects = projectRepository.findAllByOrderByDisplayOrderAscIdAsc();
        model.addAttribute("projects", projects);
        return "index";
    }

    @PostMapping("/contact")
    public String submitContact(@RequestParam String name,
                                 @RequestParam String email,
                                 @RequestParam String message,
                                 RedirectAttributes redirectAttributes) {
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(name);
        contactMessage.setEmail(email);
        contactMessage.setMessage(message);
        contactMessageRepository.save(contactMessage);

        redirectAttributes.addFlashAttribute("contactSuccess", true);
        return "redirect:/#contact";
    }
}
