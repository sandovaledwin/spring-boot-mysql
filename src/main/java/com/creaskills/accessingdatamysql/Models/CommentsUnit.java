package com.creaskills.accessingdatamysql.Models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CommentsUnit {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String description;

    @Column(nullable = false, name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "unit_id")
    private Integer unitId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "parent_id")
    private Integer parentId;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CommentsUnit{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", unitId=" + unitId +
                ", userId=" + userId +
                ", parentId=" + parentId +
                ", status='" + status + '\'' +
                '}';
    }

}
