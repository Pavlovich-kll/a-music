package com.musicapp.mapper;

import com.musicapp.domain.Notification;
import com.musicapp.dto.NotificationDto;
import org.mapstruct.*;

import java.util.List;

/**
 * Маппер сущности уведомлений.
 *
 * @author i.dubrovin
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface NotificationMapper {

    List<NotificationDto> map(List<Notification> notification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Notification updateNotification(NotificationDto notificationDto, @MappingTarget Notification notification);
}
