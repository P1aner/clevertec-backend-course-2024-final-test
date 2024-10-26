package ru.clevertec.newspaper.comment.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.newspaper.comment.api.dto.EditCommentDto;
import ru.clevertec.newspaper.comment.api.dto.FullCommentDto;
import ru.clevertec.newspaper.comment.api.dto.NewCommentDto;
import ru.clevertec.newspaper.news.api.dto.FullNewsDto;
import ru.clevertec.newspaper.news.core.News;
import ru.clevertec.newspaper.news.core.NewsMapper;
import ru.clevertec.newspaper.news.core.NewsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final CommentMapper commentMapper;
    private final NewsMapper newsMapper;

    public FullCommentDto createComment(Long newsId, NewCommentDto newCommentDto) {
        Comment comment = commentMapper.toComment(newCommentDto);
        News news = newsRepository.findById(newsId).orElseThrow(RuntimeException::new);
        comment.setNews(news);
        Comment save = commentRepository.save(comment);
        return commentMapper.toCommentDto(save);
    }

    public FullCommentDto updateComment(Long newsId, Long commentId, EditCommentDto editCommentDto) {
        Comment comment = commentRepository.findByNews_IdAndId(newsId, commentId).orElseThrow(RuntimeException::new);
        commentMapper.updateCommentFromDto(editCommentDto, comment);
        Comment save = commentRepository.save(comment);
        return commentMapper.toCommentDto(save);
    }

    public FullCommentDto findComment(Long newsId, Long commentId) {
        Comment comment = commentRepository.findByNews_IdAndId(newsId, commentId)
                .orElseThrow(RuntimeException::new);
        return commentMapper.toCommentDto(comment);
    }

    public void deleteComment(Long newsId, Long commentId) {
        commentRepository.deleteByNews_IdAndId(newsId, commentId);
    }

    public FullNewsDto getAllCommentOfNews(Long id, Pageable pageable) {
        News news = newsRepository.findById(id).orElseThrow(RuntimeException::new);
        Page<Comment> byNewsId = commentRepository.findByNews_Id(id, pageable);
        List<Comment> content = byNewsId.getContent();
        news.setCommentList(content);
        return newsMapper.toNewsDto(news);
    }

    public List<FullCommentDto> findComments(Long id, String q, Pageable pageable) {
        List<Comment> byTextContainsIgnoreCaseAndNewsId = commentRepository.findByTextContainsIgnoreCaseAndNews_Id(q, id, pageable);
        return commentMapper.toCommentListDto(byTextContainsIgnoreCaseAndNewsId);
    }
}
