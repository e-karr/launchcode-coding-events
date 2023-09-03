package com.example.codingevents.controllers;

import com.example.codingevents.data.TagRepository;
import com.example.codingevents.models.EventCategory;
import com.example.codingevents.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("eventTags")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayAllTags(Model model) {
        model.addAttribute("title", "All Tags");
        model.addAttribute("tags", tagRepository.findAll());
        return "eventTags/index";
    }

    @GetMapping("create")
    public String renderCreateTagForm(Model model) {
        model.addAttribute("title", "Create Tag");
        model.addAttribute(new Tag());
        return "eventTags/create";
    }

    @PostMapping("create")
    public String processCreateTagForm(@ModelAttribute @Valid Tag newTag,
                                                 Errors errors,
                                                 Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("hasErrors", errors.hasErrors());
            model.addAttribute("title", "Create Tag");
            return "eventTags/create";
        }
        tagRepository.save(newTag);
        return "redirect:";
    }
}
