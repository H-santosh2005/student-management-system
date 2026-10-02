package com.sms.controller;

import com.sms.exception.DuplicateEmailException;
import com.sms.model.Student;
import com.sms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/students";
    }

    // READ + SEARCH
    @GetMapping("/students")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("students", service.search(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        return "index";
    }

    // CREATE form
    @GetMapping("/students/new")
    public String newForm(Model model) {
        model.addAttribute("student", new Student());
        return "index-form";
    }

    // CREATE / UPDATE submit
    @PostMapping("/students/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result,
                       RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "index-form";
        }
        try {
            if (student.getId() == null) {
                service.register(student);
                redirect.addFlashAttribute("message", "Student registered successfully.");
            } else {
                service.update(student.getId(), student);
                redirect.addFlashAttribute("message", "Student updated successfully.");
            }
        } catch (DuplicateEmailException ex) {
            result.rejectValue("email", "duplicate", ex.getMessage());
            return "index-form";
        }
        return "redirect:/students";
    }

    // UPDATE form
    @GetMapping("/students/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", service.getById(id));
        return "index-form";
    }

    // DELETE
    @PostMapping("/students/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("message", "Student deleted successfully.");
        return "redirect:/students";
    }
}
