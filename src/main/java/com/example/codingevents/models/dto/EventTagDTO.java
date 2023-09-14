package com.example.codingevents.models.dto;

import com.example.codingevents.models.Event;
import com.example.codingevents.models.Tag;

import javax.validation.constraints.NotNull;
import java.util.List;

public class EventTagDTO {

    @NotNull
    private Event event;

    @NotNull
    private List<Tag> tags;

    public EventTagDTO() {}

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
