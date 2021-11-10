package com.creaskills.accessingdatamysql.Controllers;

import com.creaskills.accessingdatamysql.Exceptions.CommentsUnitNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseUnitNotFoundException;
import com.creaskills.accessingdatamysql.Models.CommentsUnit;
import com.creaskills.accessingdatamysql.Models.CourseUnit;
import com.creaskills.accessingdatamysql.Services.CommentsUnitManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path="/admin")
public class CommentsUnitManagement {

    @Autowired
    private CommentsUnitManagementService commentsUnitManagement;

    @GetMapping("/comments/{courseId}/{unitId}")
    List<CommentsUnit> getComments(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId
    ){
        try {
            return commentsUnitManagement.getComments(courseId, unitId);
        } catch (CourseNotFoundException | CourseUnitNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "There isn't any comments", ex);
        }
    }

    @GetMapping("/comment/{courseId}/{unitId}/{commentId}")
    CommentsUnit getComment(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @PathVariable Integer commentId){
        try {
            return commentsUnitManagement.getComment(courseId, unitId, commentId);
        } catch (
                CourseNotFoundException |
                CourseUnitNotFoundException |
                CommentsUnitNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The comment doesn't exist", ex);
        }
    }

    @DeleteMapping("/comment/{courseId}/{unitId}/{commentId}")
    void deleteComment(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @PathVariable Integer commentId){
        try {
            commentsUnitManagement.deleteComment(courseId, unitId, commentId);
        } catch (
                CourseNotFoundException |
                CourseUnitNotFoundException |
                CommentsUnitNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The comment doesn't exist", ex);
        }
    }

    @PostMapping("/comment/{courseId}/{unitId}")
    CommentsUnit postComment(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @RequestBody CommentsUnit comment){
        try {
            return commentsUnitManagement.postComment(courseId, unitId, comment);
        } catch (
                CourseNotFoundException | CourseUnitNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course or unit don't exist", ex);
        }
    }

    @PutMapping("/comment/{courseId}/{unitId}")
    CommentsUnit updateComment(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @RequestBody CommentsUnit comment){
        try {
            return commentsUnitManagement.postComment(courseId, unitId, comment);
        } catch (
                CourseNotFoundException | CourseUnitNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course or unit don't exist", ex);
        }
    }
}
