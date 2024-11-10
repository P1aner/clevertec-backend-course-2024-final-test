package ru.clevertec.newspaper.core.comment.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ADMIN') || (hasRole('SUBSCRIBER') && @commentService.isAuthor(#newsId, #commentId, authentication.name))")
public @interface AdminAndCommentOwnerCanEdit {
}
