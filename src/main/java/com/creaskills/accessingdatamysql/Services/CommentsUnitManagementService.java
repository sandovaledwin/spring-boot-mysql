package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Models.CommentsUnit;

import java.util.List;

public interface CommentsUnitManagementService {
    CommentsUnit getComment(Integer courseId, Integer unitId, Integer commentId);
    List<CommentsUnit> getComments(Integer courseId, Integer unitId);
    void deleteComment(Integer courseId, Integer unitId, Integer commentId);
    CommentsUnit postComment(Integer courseId, Integer unitId, CommentsUnit comment);
}
