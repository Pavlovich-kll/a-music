package com.musicapp.web.controller;

import com.musicapp.domain.OrderDetail;
import com.musicapp.service.PaymentService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллер оплаты PayPal.
 *
 * @author lizavetashpinkova
 */
@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PaymentController {

    private final PaymentService paymentService;

    public static final String CANCEL_URL = "/cancel";
    public static final String SUCCESS_URL = "/success";

    /**
     * Метод для авторизации платежа.
     *
     * @param orderDetail детали платежа
     * @return url для авторизации и подтверждения платежа на сервере PayPal
     * @throws PayPalRESTException
     */
    @ApiOperation("Метод для авторизации платежа.")
    @PostMapping
    public ResponseEntity<String> authorizePayment(HttpServletRequest request, @RequestBody OrderDetail orderDetail) throws PayPalRESTException {

        String url = request.getRequestURL().toString();

        String approvalLink = paymentService.authorizePayment(orderDetail, url.concat(SUCCESS_URL), url.concat(CANCEL_URL));

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, approvalLink).build();
    }

    /**
     * Метод для отмены платежа.
     */
    @ApiOperation("Метод для отмены платежа.")
    @GetMapping(value = CANCEL_URL)
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Метод для завершения платежа.
     *
     * @param paymentId id платежа
     * @param payerId   id плательщика
     * @return статус выполнения операции
     * @throws PayPalRESTException
     */
    @ApiOperation("Метод для завершения платежа.")
    @GetMapping(value = SUCCESS_URL)
    public ResponseEntity<String> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId
    ) throws PayPalRESTException {

        Payment payment = paymentService.executePayment(paymentId, payerId);

        return (payment.getState().equals("approved") ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    }
}
