package ru.clevertec.newspaper.core.comment;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    void createComment() throws Exception {
        CommentDetailsDto commentDetailsDto = CommentDataTest.commentDetailsDto();

        Mockito.when(commentService.createComment(Mockito.anyLong(), Mockito.any())).thenReturn(commentDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/news/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CommentDataTest.NEW_COMMENT))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json(CommentDataTest.NEW_COMMENT_WITH_ID));
    }

    @Test
    void getComment() throws Exception {
        CommentDetailsDto commentDetailsDto = CommentDataTest.commentDetailsDto();

        Mockito.when(commentService.getComment(Mockito.anyLong(), Mockito.anyLong())).thenReturn(commentDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/news/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(CommentDataTest.NEW_COMMENT_WITH_ID));
    }

    @Test
    void updateComment() throws Exception {
        CommentDetailsDto commentDetailsDto = CommentDataTest.commentDetailsDto();

        Mockito.when(commentService.updateComment(Mockito.anyLong(), Mockito.anyLong(), Mockito.any())).thenReturn(commentDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/news/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CommentDataTest.UPDATE_COMMENT))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(CommentDataTest.NEW_COMMENT_WITH_ID));
    }

    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/news/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void findCommentByNews() throws Exception {
        NewsDetailsDto newsDetailsDto = CommentDataTest.newsDetailsDto();

        Mockito.when(commentService.findCommentByNews(Mockito.anyLong(), Mockito.any(), Mockito.any())).thenReturn(newsDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/news/1/comments")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(CommentDataTest.COMMENTS_LIST));
    }
}