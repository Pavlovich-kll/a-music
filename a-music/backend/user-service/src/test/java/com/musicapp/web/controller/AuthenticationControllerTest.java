package com.musicapp.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.dto.AuthenticationRequestDto;
import com.musicapp.security.oauth2.Oauth2SuccessHandler;
import com.musicapp.security.oauth2.Oauth2UserServiceImpl;
import com.musicapp.security.oauth2.OidcUserServiceImpl;
import com.musicapp.service.AuthenticationService;
import com.musicapp.service.TokenService;
import com.musicapp.util.constants.ProfileConstants;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(ProfileConstants.LOCAL)
@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@MockBean({TokenService.class, UserDetailsService.class, AuthenticationService.class,
        Oauth2UserServiceImpl.class, OidcUserServiceImpl.class, Oauth2SuccessHandler.class, ClientRegistrationRepository.class})
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AuthenticationService authenticationService;

    private AuthenticationRequestDto requestDtoByPhone;
    private AuthenticationRequestDto requestDtoByEmail;
    private String uri;
    private String phoneNumber;
    private String getTokenUri;

    @Before
    public void setUp() {
        requestDtoByPhone = AuthenticationRequestDto.builder()
                .withUsername("+380981112233")
                .withPassword("testtest")
                .build();

        requestDtoByEmail = AuthenticationRequestDto.builder()
                .withUsername("edit@mail.ru")
                .withPassword("testtest")
                .build();

        phoneNumber = "+380981112233";
        uri = "/auth";
        getTokenUri = uri + "/get-token";
    }

    @Test
    public void givenNoTokenByPhone_whenGetSecureRequest_thenUnauthorized() throws Exception {

        mvc.perform(get("/users/update/44")
                .param("username", phoneNumber))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authenticateByPhone() throws Exception {

        when(authenticationService.authenticate(phoneNumber, "testtest")).thenReturn("token");

        mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(requestDtoByPhone)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    public void givenNoTokenByEmail_whenGetSecureRequest_thenUnauthorized() throws Exception {

        mvc.perform(get("/users/update/44")
                .param("username", "edit@mail.ru"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authenticateByEmail() throws Exception {
        when(authenticationService.authenticate("edit@mail.ru", "testtest")).thenReturn("token");

        mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(requestDtoByEmail)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Deprecated
    @Test
    public void tokenWhenOauth2AuthorizedDeprecated() throws Exception {
        mvc.perform(get(getTokenUri + "/token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    public void tokenWhenOauth2Authorized() throws Exception {
        mvc.perform(get(uri + "/token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    public void tokenWhenOauth2NotAuthorized() throws Exception {
        mvc.perform(get(uri))
                .andExpect(status().is4xxClientError());
    }
}