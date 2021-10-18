package com.creaskills.accessingdatamysql.Models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CourseUnit {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String title;

    private String description;

    @Column(nullable = false, name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "course_id")
    private Integer courseId;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseUnits{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", courseId=" + courseId +
                ", status='" + status + '\'' +
                '}';
    }
}
