package ru.clevertec.newspaper.core.news;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;
import ru.clevertec.newspaper.core.comment.CommentMapper;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.SETTER,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {CommentMapper.class},
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface NewsMapper {
    @Mapping(source = "localDateTime", target = "timestamp", qualifiedByName = "localDateTimeToTimestamp")
    @Mapping(source = "commentList", target = "commentListList")
    NewsDetailsDto toNewsDto(News news);

    @Mapping(source = "timestamp", target = "localDateTime", qualifiedByName = "timestampToLocalDateTime")
    News toNews(NewNewsDto news);

    NewsTitleDto toNewsTitleDto(News news);

    List<NewsTitleDto> toShortNewsListDto(List<News> news);

    @Mapping(source = "timestamp", target = "localDateTime", qualifiedByName = "timestampToLocalDateTime")
    void updateNewsFromDto(UpdateNewsDto dto, @MappingTarget News appUser);


}
