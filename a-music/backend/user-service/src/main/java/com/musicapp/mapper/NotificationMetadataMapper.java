package com.musicapp.mapper;

import com.musicapp.domain.NotificationMetadata;
import com.musicapp.dto.NotificationMetadataDto;
import org.mapstruct.*;

import java.util.List;

/**
 * Маппер сущности метаданных уведомлений.
 *
 * @author i.dubrovin
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface NotificationMetadataMapper {

    List<NotificationMetadataDto> map(List<NotificationMetadata> notificationMetadata);

    NotificationMetadata map(NotificationMetadataDto notificationMetadataDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NotificationMetadata updateNotificationMetadata(NotificationMetadataDto notificationMetadataDto,
                                                    @MappingTarget NotificationMetadata notificationMetadata);
}
