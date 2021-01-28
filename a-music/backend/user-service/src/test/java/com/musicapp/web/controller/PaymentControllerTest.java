package com.musicapp.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.domain.OrderDetail;
import com.musicapp.service.PaymentService;
import com.musicapp.web.config.MockSpringSecurityTestConfiguration;
import com.paypal.api.payments.Payment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.NestedServletException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
@Import(MockSpringSecurityTestConfiguration.class)
@WithMockUser
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentServiceMock;

    private String approvalLink;
    private String orderDetailJsonDto;
    private OrderDetail orderDetail;
    private Payment payment;

    private static final String PAY_ENDPOINT = "/pay";
    private static final String CANCEL_ENDPOINT = "/cancel";
    private static final String SUCCESS_ENDPOINT = "/success";

    @Before
    public void setUp() throws JsonProcessingException {

        orderDetail = new OrderDetail("Prime", "USD", "50");
        orderDetailJsonDto = objectMapper.writeValueAsString(orderDetail);
        payment = new Payment();
        approvalLink = "";
    }


    @Test(expected = NestedServletException.class)
    public void whenAuthorizePayment_thenRedirect() throws Exception {

        when(paymentServiceMock.authorizePayment(orderDetail, SUCCESS_ENDPOINT, CANCEL_ENDPOINT)).thenReturn(approvalLink);

        mockMvc.perform(post(PAY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(orderDetailJsonDto))
                .andDo(print())
                .andExpect(header().string(HttpHeaders.LOCATION, approvalLink))
                .andExpect(status().isMovedPermanently());
    }

    @Test
    public void cancelPay() throws Exception {

        mockMvc.perform(get(PAY_ENDPOINT.concat(CANCEL_ENDPOINT)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void successPay() throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("paymentId", "1");
        requestParams.add("PayerID", "1");

        when(paymentServiceMock.executePayment("1", "1")).thenReturn(payment);

        payment.setState("approved");

        mockMvc.perform(get(PAY_ENDPOINT.concat(SUCCESS_ENDPOINT))
                .params(requestParams))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        payment.setState("");

        mockMvc.perform(get(PAY_ENDPOINT.concat(SUCCESS_ENDPOINT))
                .params(requestParams))
                .andDo(print())
                .andExpect(status().isNotModified())
                .andReturn();
    }
}


