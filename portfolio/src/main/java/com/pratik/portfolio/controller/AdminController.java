package com.pratik.portfolio.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pratik.portfolio.model.Project;
import com.pratik.portfolio.repository.ContactMessageRepository;
import com.pratik.portfolio.repository.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProjectRepository projectRepository;
    private final ContactMessageRepository contactMessageRepository;

    public AdminController(ProjectRepository projectRepository,
                            ContactMessageRepository contactMessageRepository) {
        this.projectRepository = projectRepository;
        this.contactMessageRepository = contactMessageRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Project> projects = projectRepository.findAllByOrderByDisplayOrderAscIdAsc();
        model.addAttribute("projects", projects);
        model.addAttribute("messageCount", contactMessageRepository.count());
        return "admin/dashboard";
    }

    @GetMapping("/projects/new")
    public String newProjectForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("isNew", true);
        return "admin/project-form";
    }

    @GetMapping("/projects/edit/{id}")
    public String editProjectForm(@PathVariable Long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + id));
        model.addAttribute("project", project);
        model.addAttribute("isNew", false);
        return "admin/project-form";
    }

    @PostMapping("/projects/save")
    public String saveProject(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
        projectRepository.save(project);
        redirectAttributes.addFlashAttribute("saved", true);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("deleted", true);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        model.addAttribute("messages", contactMessageRepository.findAllByOrderBySubmittedAtDesc());
        return "admin/messages";
    }
}
