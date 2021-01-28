package com.musicapp.service.impl;

import com.musicapp.domain.OrderDetail;
import com.musicapp.service.PaymentService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для управления платежом.
 *
 * @author lizavetashpinkova
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final APIContext apiContext;

    private final static String PAYMENT_METHOD = "paypal";
    private final static String APPROVAL_LINK = "approval_url";
    private final static String PAYMENT_INTENT = "authorize";

    /**
     * Метод создания платежа.
     *
     * @param redirectUrls URL-адреса, на которые PayPal будет перенаправлять в процессе оформления заказа
     * @param payer        плательщик
     * @return платеж
     */
    private Payment createPayment(List<Transaction> transactions, RedirectUrls redirectUrls, Payer payer) {

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(transactions);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent(PAYMENT_INTENT);

        return requestPayment;
    }

    /**
     * Метод возвращает информацию платежах,
     * которые будут отражаться на странице оплаты PayPal.
     *
     * @param orderDetail детали платежа
     */
    private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {

        Amount amount = new Amount();
        amount.setCurrency(orderDetail.getCurrency());
        amount.setTotal(orderDetail.getPrice());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getProductName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency(orderDetail.getCurrency());
        item.setName(orderDetail.getProductName());
        item.setPrice(orderDetail.getPrice());
        item.setQuantity("1");

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        return transactions;
    }

    /**
     * Анализирует утвержденный объект Payment, возвращенный PayPal,
     * чтобы найти URL-адрес утверждения в ответе JSON,
     * возвращает ссылку для перехода на авторизацию на сервере PayPal.
     *
     * @param approvedPayment одобренный платеж
     * @return url для авторизации и подтвержения платежа
     */
    private String getApprovalLink(Payment approvedPayment) {

        List<Links> links = approvedPayment.getLinks();

        return links.stream()
                .filter(link -> APPROVAL_LINK.equalsIgnoreCase(link.getRel()))
                .map(Links::getHref)
                .collect(Collectors.joining());
    }

    /**
     * Метод получения информации о плательщике.
     *
     * @return плательщика
     */
    private Payer getPayerInformation() {

        Payer payer = new Payer();
        payer.setPaymentMethod(PAYMENT_METHOD);

        return payer;
    }

    @Override
    @Transactional
    public String authorizePayment(OrderDetail orderDetail, String successUrl, String cancelUrl) throws PayPalRESTException {

        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = new RedirectUrls().setCancelUrl(cancelUrl).setReturnUrl(successUrl);
        List<Transaction> transactions = getTransactionInformation(orderDetail);

        Payment requestPayment = createPayment(transactions, redirectUrls, payer);

        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);
    }

    @Override
    @Transactional
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {

        Payment payment = new Payment().setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}
