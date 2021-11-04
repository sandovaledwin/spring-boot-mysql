package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Components.IAuthenticationFacade;
import com.creaskills.accessingdatamysql.Exceptions.CommentsUnitNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseUnitNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.UserNotFoundException;
import com.creaskills.accessingdatamysql.Models.CommentsUnit;
import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.CourseUnit;
import com.creaskills.accessingdatamysql.Models.User;
import com.creaskills.accessingdatamysql.Repositories.CommentsUnitRepository;
import com.creaskills.accessingdatamysql.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentsUnitManagementServiceImpl implements CommentsUnitManagementService {
    @Autowired
    private CommentsUnitRepository commentsUnitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public CommentsUnit getComment(Integer courseId, Integer unitId, Integer commentId) {
        return getCommentBy(courseId, unitId, commentId);
    }

    public void deleteComment(Integer courseId, Integer unitId, Integer commentId) {
        commentsUnitRepository.deleteById(
                getCommentBy(courseId, unitId, commentId).getId()
        );
    }

    public CommentsUnit postComment(Integer courseId, Integer unitId, CommentsUnit comment) {
        CommentsUnit newComment = new CommentsUnit();
        if (comment.getId() != null & comment.getUserId() != null) {
            CommentsUnit refComment = getOwnComment(courseId, unitId, comment.getId(), comment.getUserId());
            newComment.setId(refComment.getId());
            newComment.setUserId(refComment.getUserId());
        }
        newComment.setDescription(comment.getDescription());
        newComment.setCreationDate(LocalDateTime.now());
        newComment.setParentId(getCommentBy(courseId, unitId, comment.getParentId()).getId());
        newComment.setUnitId(getCourseUnit(courseId, unitId).getId());
        newComment.setStatus(comment.getStatus());
        return commentsUnitRepository.save(newComment);
    }

    public List<CommentsUnit> getComments(Integer courseId, Integer unitId) {
        return getCourseUnit(courseId, unitId).getComments();
    }

    private User getUserInstance() {
        String currentUser = authenticationFacade.getUserName();
        return userRepository.findByUserName(currentUser)
                .orElseThrow(() -> new UserNotFoundException(currentUser));
    }

    private Course getCourseById(Integer courseId) {
        return  getUserInstance()
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    private CourseUnit getCourseUnit(Integer courseId, Integer unitId) {
        return getCourseById(courseId)
                .getUnits()
                .stream()
                .filter(u -> u.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitId));
    }

    private CommentsUnit getCommentBy(Integer courseId, Integer unitId, Integer commentId) {
        return getCourseUnit(courseId, unitId)
                .getComments()
                .stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentsUnitNotFoundException(commentId));
    }

    private CommentsUnit getOwnComment(
            Integer courseId,
            Integer unitId,
            Integer commentId,
            Integer userId ) {
        return getCourseUnit(courseId, unitId)
                .getComments()
                .stream()
                .filter(c -> c.getId().equals(commentId) & c.getUnitId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new CommentsUnitNotFoundException(commentId));
    }
}
