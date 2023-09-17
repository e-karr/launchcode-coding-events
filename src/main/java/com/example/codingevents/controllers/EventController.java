package com.example.codingevents.controllers;

import com.example.codingevents.data.EventCategoryRepository;
import com.example.codingevents.data.EventRepository;
import com.example.codingevents.data.TagRepository;
import com.example.codingevents.models.Event;
import com.example.codingevents.models.EventCategory;
import com.example.codingevents.models.Tag;
import com.example.codingevents.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayEvents(@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer tagId, Model model) {
        if (categoryId == null && tagId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        } else if (categoryId != null) {
           Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
           if (result.isEmpty()) {
               model.addAttribute("title", "Invalid Category ID: " + categoryId);
           } else {
               EventCategory category = result.get();
               model.addAttribute("title", "All " + category.getName() + " Events");
               model.addAttribute("events", category.getEvents());
           }
        } else {
            Optional<Tag> result = tagRepository.findById(tagId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Tag ID: " + tagId);
            } else {
                Tag tag = result.get();
                model.addAttribute("title", "All " + tag.getDisplayName() + " Events");
                model.addAttribute("events", tag.getEvents());
            }
        }

        return "events/index";
    }

    @GetMapping("details")
    public String displayEventDetails(@RequestParam(required = false) Integer eventId, Model model) {
        if (eventId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        } else {
            Optional<Event> result = eventRepository.findById(eventId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Event ID: " + eventId);
            } else {
                Event event = result.get();
                model.addAttribute("title", event.getName() + " Details");
                model.addAttribute("event", event);
                model.addAttribute("tags", event.getTags());
            }
        }

        return "events/details";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("hasErrors", errors.hasErrors());
            model.addAttribute("title", "Create Event");
            return "events/create";
        }
        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventsForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }

        return "redirect:";
    }

    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model) {
        Optional<Event> result = eventRepository.findById(eventId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Event");
            return "redirect:";
        }

        Event event = result.get();

        model.addAttribute("title", "Add Tag to: " + event.getName());
        model.addAttribute("tags", tagRepository.findAll());
        EventTagDTO eventTag = new EventTagDTO();
        eventTag.setEvent(event);
        model.addAttribute("eventTag", eventTag);

        return "events/add-tag";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag, Errors errors, Model model) {
        if (!errors.hasErrors()) {
            Event event = eventTag.getEvent();
            List<Tag> tags = eventTag.getTags();

            for (Tag tag : tags) {
                if (!event.getTags().contains(tag)) {
                    event.addTag(tag);
                    eventRepository.save(event);
                }
            }

            return "redirect:details?eventId=" + event.getId();
        }

        return "redirect:/add-tag";
    }
}
