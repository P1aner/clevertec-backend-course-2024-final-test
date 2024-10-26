package ru.clevertec.newspaper.news.core;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.newspaper.comment.core.CommentMapper;
import ru.clevertec.newspaper.news.api.dto.EdinNewsDto;
import ru.clevertec.newspaper.news.api.dto.FullNewsDto;
import ru.clevertec.newspaper.news.api.dto.NewNewsDto;
import ru.clevertec.newspaper.news.api.dto.ShortNewsDto;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CommentMapper.class})
public interface NewsMapper {

    FullNewsDto toNewsDto(News news);

    News toNews(NewNewsDto news);

    List<ShortNewsDto> toShortNewsListDto(List<News> news);

    void updateNewsFromDto(EdinNewsDto dto, @MappingTarget News appUser);

}
