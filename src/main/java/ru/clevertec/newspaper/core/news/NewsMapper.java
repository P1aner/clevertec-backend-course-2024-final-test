package ru.clevertec.newspaper.core.news;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;
import ru.clevertec.newspaper.core.comment.CommentMapper;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CommentMapper.class})
public interface NewsMapper {

    NewsDetailsDto toNewsDto(News news);

    News toNews(NewNewsDto news);

    List<NewsTitleDto> toShortNewsListDto(List<News> news);

    void updateNewsFromDto(UpdateNewsDto dto, @MappingTarget News appUser);

}
