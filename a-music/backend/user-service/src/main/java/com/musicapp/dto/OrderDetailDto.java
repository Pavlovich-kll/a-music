package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto-представление деталей заказа.
 *
 * @author lizavetashpinkova
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    private String productName;
    private String currency;
    private String price;
}
