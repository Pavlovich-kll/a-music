package com.musicapp.mapper;

import com.musicapp.domain.OrderDetail;
import com.musicapp.dto.OrderDetailDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetailDto map(OrderDetail orderDetail);
}
