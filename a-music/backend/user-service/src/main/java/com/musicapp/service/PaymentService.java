package com.musicapp.service;

import com.musicapp.domain.OrderDetail;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Интерфейс сервиса для управления платежом.
 *
 * @author lizavetashpinkova
 */
public interface PaymentService {

    /**
     * Метод для авторизации платежа.
     *
     * @param orderDetail детали заказа
     * @param successUrl  url для перехода, в случае подтверждения платежа
     * @param cancelUrl   url для перехода, в случае отмены
     * @return url для авторизации пользователя на сервере PayPal
     * @throws PayPalRESTException
     */
    String authorizePayment(OrderDetail orderDetail, String successUrl, String cancelUrl) throws PayPalRESTException;

    /**
     * Метод для выполнения платежа в случае подтверждения на сервере PayPal.
     *
     * @param paymentId id платежа
     * @param payerId   id плательщика
     * @return выполненный платеж
     * @throws PayPalRESTException
     */
    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
