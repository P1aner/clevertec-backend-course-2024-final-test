package ru.clevertec.newspaper.core.comment;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.config.sequrity.SecurityConfig;
import ru.clevertec.newspaper.core.comment.controller.CommentController;
import ru.clevertec.newspaper.core.comment.service.CommentServiceBase;
import ru.clevertec.newspaper.core.secure.SecureData;

@WebMvcTest({CommentController.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@Import(SecurityConfig.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentServiceBase commentService;

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void createComment() throws Exception {
        CommentDetailsDto commentDetailsDto = CommentData.commentDetailsDto();

        Mockito.when(commentService.createComment(Mockito.anyLong(), Mockito.any())).thenReturn(commentDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/news/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .header(SecureData.AUTHORIZATION, SecureData.BASIC)
                .content(CommentData.NEW_COMMENT))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json(CommentData.NEW_COMMENT_WITH_ID));
    }

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void getComment() throws Exception {
        CommentDetailsDto commentDetailsDto = CommentData.commentDetailsDto();

        Mockito.when(commentService.getComment(Mockito.anyLong(), Mockito.anyLong())).thenReturn(commentDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/news/1/comments/1")
                .header(SecureData.AUTHORIZATION, SecureData.BASIC)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(CommentData.NEW_COMMENT_WITH_ID));
    }

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void updateComment() throws Exception {
        CommentDetailsDto commentDetailsDto = CommentData.commentDetailsDto();

        Mockito.when(commentService.updateComment(Mockito.anyLong(), Mockito.anyLong(), Mockito.any())).thenReturn(commentDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/news/1/comments/1")
                .header(SecureData.AUTHORIZATION, SecureData.BASIC)
                .contentType(MediaType.APPLICATION_JSON)
                .content(CommentData.UPDATE_COMMENT))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(CommentData.NEW_COMMENT_WITH_ID));
    }

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void deleteComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/news/1/comments/1")
                .header(SecureData.AUTHORIZATION, SecureData.BASIC)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void findCommentByNews() throws Exception {
        NewsDetailsDto newsDetailsDto = CommentData.newsDetailsDto();

        Mockito.when(commentService.findCommentByNews(Mockito.anyLong(), Mockito.any(), Mockito.any())).thenReturn(newsDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/news/1/comments")
                .header(SecureData.AUTHORIZATION, SecureData.BASIC)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(CommentData.COMMENTS_LIST));
    }
}
