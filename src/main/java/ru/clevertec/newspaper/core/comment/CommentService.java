package ru.clevertec.newspaper.core.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.core.news.News;
import ru.clevertec.newspaper.core.news.NewsMapper;
import ru.clevertec.newspaper.core.news.NewsRepository;
import ru.clevertec.newspaper.exception.ProblemUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final CommentMapper commentMapper;
    private final NewsMapper newsMapper;

    public CommentDetailsDto createComment(Long newsId, NewCommentDto newCommentDto) {
        Comment comment = commentMapper.toComment(newCommentDto);
        News news = newsRepository.findById(newsId)
            .orElseThrow(() -> ProblemUtils.newsNotFound(newsId));
        comment.setNews(news);
        Comment save = commentRepository.save(comment);
        return commentMapper.toCommentDto(save);
    }

    public CommentDetailsDto getComment(Long newsId, Long commentId) {
        Comment comment = commentRepository.findByNews_IdAndId(newsId, commentId)
            .orElseThrow(() -> ProblemUtils.commentNotFound(commentId));
        return commentMapper.toCommentDto(comment);
    }

    public CommentDetailsDto updateComment(Long newsId, Long commentId, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findByNews_IdAndId(newsId, commentId)
            .orElseThrow(() -> ProblemUtils.commentNotFound(commentId));
        commentMapper.updateCommentFromDto(updateCommentDto, comment);
        Comment save = commentRepository.save(comment);
        return commentMapper.toCommentDto(save);
    }

    public void deleteComment(Long newsId, Long commentId) {
        commentRepository.deleteByNews_IdAndId(newsId, commentId);
    }

    public NewsDetailsDto findCommentByNews(Long id, String query, Pageable pageable) {
        News news = newsRepository.findById(id)
            .orElseThrow(() -> ProblemUtils.newsNotFound(id));
        Page<Comment> comments;
        if (StringUtils.isEmpty(query)) {
            log.debug("Query is empty");
            comments = commentRepository.findByNews_Id(id, pageable);
        } else {
            log.debug("Query is not empty");
            comments = commentRepository.findByTextContainsIgnoreCaseAndNews_Id(query, id, pageable);
        }
        List<Comment> content = comments.getContent();
        news.setCommentList(content);
        return newsMapper.toNewsDto(news);

    }
}
