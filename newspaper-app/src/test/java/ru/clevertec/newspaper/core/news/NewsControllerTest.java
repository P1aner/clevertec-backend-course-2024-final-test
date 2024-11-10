package ru.clevertec.newspaper.core.news;

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
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.config.sequrity.SecurityConfig;
import ru.clevertec.newspaper.core.news.controller.NewsController;
import ru.clevertec.newspaper.core.news.service.NewsServiceBase;
import ru.clevertec.newspaper.core.secure.SecureData;

import java.util.List;

@WebMvcTest(NewsController.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@Import(SecurityConfig.class)
class NewsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsServiceBase newsService;

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void createNews() throws Exception {
        NewsDetailsDto newsDetailsDto = NewsData.newsDetailsDtoWithoutComments();

        Mockito.when(newsService.createNews(Mockito.any())).thenReturn(newsDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/news")
                .header(SecureData.AUTHORIZATION, SecureData.BASIC)
                .contentType(MediaType.APPLICATION_JSON)
                .content(NewsData.NEW_NEWS_CONTENT))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json(NewsData.FULL_NEWS));
    }

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void getNews() throws Exception {
        NewsDetailsDto newsDetailsDto = NewsData.newsDetailsDtoWithoutComments();

        Mockito.when(newsService.findNews(Mockito.any())).thenReturn(newsDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/news/1")
                .header(SecureData.AUTHORIZATION, SecureData.BASIC)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(NewsData.FULL_NEWS));
    }

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void updateNews() throws Exception {
        NewsDetailsDto newsDetailsDto = NewsData.newsDetailsDtoWithoutComments();

        Mockito.when(newsService.updateNews(Mockito.any(), Mockito.any())).thenReturn(newsDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/news/1")
                .header(SecureData.AUTHORIZATION, SecureData.BASIC)
                .contentType(MediaType.APPLICATION_JSON)
                .content(NewsData.NEW_NEWS_CONTENT))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(NewsData.FULL_NEWS));
    }

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void deleteNews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/news/1")
                .header(SecureData.AUTHORIZATION, SecureData.BASIC))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "root", roles = "ADMIN")
    void findNews() throws Exception {
        NewsTitleDto newsTitleDto = NewsData.newsTitleDto();
        Mockito.when(newsService.findNews(Mockito.any(), Mockito.any())).thenReturn(List.of(newsTitleDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/news")
                .header(SecureData.AUTHORIZATION, SecureData.BASIC)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(NewsData.TITLE_NEWS_LIST));
    }
}
