package com.example.codingevents.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

public class Event {
    private final int id;
    private static int nextId = 0;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
    private String name;

    @Size(max = 500, message = "Description too long!")
    private String description;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

    @NotNull
    @NotBlank(message = "Location is required.")
    @Size(min = 3, max = 100, message = "Location must be between 3 and 100 characters.")
    private String location;

    @AssertTrue
    private boolean registrationRequired;

    @Positive(message = "Event must have at least 1 attendee.")
    private int attendees;

    @NotNull(message = "Date is required.")
    @Future(message = "Event must take place on a future date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    public Event() {
        this.id = nextId;
        nextId++;
    }

    public Event(String name, String description, String contactEmail, String location, int attendees, LocalDate date) {
        this();
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
        this.location = location;
        this.attendees = attendees;
        this.eventDate = date;
    }

    public Event(String name, String description, String contactEmail, String location, int attendees, LocalDate date, boolean registrationRequired) {
        this(name, description, contactEmail, location, attendees, date);
        if (registrationRequired) {
            this.registrationRequired = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isRegistrationRequired() {
        return registrationRequired;
    }

    public void setRegistrationRequired(boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
