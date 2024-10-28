package ru.clevertec.newspaper.core.comment;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByNews_Id(Long newsId, Pageable pageable);

    Optional<Comment> findByNews_IdAndId(Long newsId, Long commentId);

    void deleteByNews_IdAndId(Long newsId, Long commentId);

    Page<Comment> findByTextContainsIgnoreCaseAndNews_Id(String text, Long id, Pageable pageable);
}
