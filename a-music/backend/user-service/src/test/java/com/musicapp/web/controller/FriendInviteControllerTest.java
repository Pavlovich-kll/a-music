package com.musicapp.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.domain.*;
import com.musicapp.dto.FriendInviteDto;
import com.musicapp.dto.UserDto;
import com.musicapp.dto.UserSubscriptionDto;
import com.musicapp.security.AuthorizedUser;
import com.musicapp.security.oauth2.Oauth2SuccessHandler;
import com.musicapp.security.oauth2.Oauth2UserServiceImpl;
import com.musicapp.security.oauth2.OidcUserServiceImpl;
import com.musicapp.service.FriendService;
import com.musicapp.service.TokenService;
import com.musicapp.service.UserService;
import com.musicapp.web.config.TokenServiceTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(value = FriendController.class)
@Import(TokenServiceTestConfig.class)
@MockBean({UserDetailsService.class, UserService.class, FriendService.class, Oauth2UserServiceImpl.class,
        Oauth2SuccessHandler.class, OidcUserServiceImpl.class, ClientRegistrationRepository.class})
@WithMockUser
public class FriendInviteControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    FriendService friendService;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    ObjectMapper mapper;

    private String uri;
    private User userInitiator;
    private User userAcceptor;
    private String mirrorInviteDyoJson;
    private String newInviteDtoJson;
    private String acceptedInviteDtoJson;
    private List<User> currentUserRepo;
    private String userAcceptorIdJson;
    private Pageable pageable;
    private UserDto userAcceptorProjection;
    private Page expectingPageOfFriends;
    private String expectingPageOfFriendsJson;
    private String pageableJson;
    private AuthorizedUser authorizedUser;
    private String token;
    private City odessaCity;
    private String cityName = "Odessa";
    private String countryName = "Ukraine";
    private Country ukraineCountry;
    private UserSubscriptionDto userSubscriptionDto;

    @Before
    public void setUp() throws JsonProcessingException {
        uri = "/friend";

        ukraineCountry = new Country();
        ukraineCountry.setId(1L);
        ukraineCountry.setCountryName(countryName);

        odessaCity = new City();
        odessaCity.setId(1L);
        odessaCity.setCityName(cityName);
        odessaCity.setCountry(ukraineCountry);

        userInitiator = new User()
                .setId(1L)
                .setFirstName("Initiator")
                .setLastName("Bot")
                .setEmail("initiator@gmail.com")
                .setPhone("+11111111111")
                .setUsername("InitiatorBot")
                .setPassword("testtest")
                .setDateOfBirth(LocalDate.of(1995, 11, 21))
                .setEnabled(true)
                .setSocial(Social.FACEBOOK)
                .setRole(Role.ROLE_USER)
                .setCity(odessaCity);

        userAcceptor = new User()
                .setId(2L)
                .setFirstName("Acceptor")
                .setLastName("Bot")
                .setEmail("acceptor@gmail.com")
                .setPhone("+22222222222")
                .setUsername("AcceptorBot")
                .setPassword("testtest")
                .setDateOfBirth(LocalDate.of(1995, 11, 21))
                .setEnabled(true)
                .setSocial(Social.FACEBOOK)
                .setRole(Role.ROLE_USER)
                .setCity(odessaCity);

        userAcceptorIdJson = mapper.writeValueAsString(userAcceptor.getId());

        authorizedUser = new AuthorizedUser(userInitiator.getId(), userInitiator.getUsername(), Collections.singleton(Role.ROLE_USER), userInitiator.getPhone(), userInitiator.getEmail());

        currentUserRepo = new ArrayList<>();
        currentUserRepo.add(userInitiator);
        currentUserRepo.add(userAcceptor);

        FriendInviteDto newInviteDto = FriendInviteDto.builder()
                .withInitiatorUsername(userInitiator.getUsername())
                .withAcceptorUsername(userAcceptor.getUsername())
                .withStatus(FriendStatus.PENDING)
                .build();

        newInviteDtoJson = mapper.writeValueAsString(newInviteDto);

        FriendInviteDto mirrorInviteDto = FriendInviteDto.builder()
                .withInitiatorUsername(userAcceptor.getUsername())
                .withAcceptorUsername(userInitiator.getUsername())
                .withStatus(FriendStatus.PENDING)
                .build();

        mirrorInviteDyoJson = mapper.writeValueAsString(mirrorInviteDto);

        FriendInviteDto acceptedInviteDto = FriendInviteDto.builder()
                .withInitiatorUsername(userInitiator.getUsername())
                .withAcceptorUsername(userAcceptor.getUsername())
                .withStatus(FriendStatus.ADDED)
                .build();

        acceptedInviteDtoJson = mapper.writeValueAsString(acceptedInviteDto);

        userAcceptorProjection = new UserDto(2L, "Acceptor", "Bot", "acceptor@gmail.com"
                , "+22222222222", "AcceptorBot", LocalDate.of(1995, 11, 21)
                , null, odessaCity, userSubscriptionDto);
        List<UserDto> listOfFriends = new ArrayList<>();
        listOfFriends.add(userAcceptorProjection);
        pageable = PageRequest.of(0, 10, Sort.by("id"));
        expectingPageOfFriends = new  PageImpl(listOfFriends, pageable, 10);
        expectingPageOfFriendsJson = mapper.writeValueAsString(expectingPageOfFriends);
        pageableJson = mapper.writeValueAsString(pageable);
        token = tokenService.generate(authorizedUser);
        when(userService.getById(any())).thenAnswer(
                a -> currentUserRepo
                        .stream()
                        .filter(f -> f.getId() == a.getArgument(0))
                        .findAny().get());
    }

    @Test
    public void addFriend() throws Exception {
        when(friendService.makeInvite(any(), any())).thenAnswer(invocation -> {
            FriendInvite friendInvite = new FriendInvite();
            friendInvite.setId(1L);
            friendInvite.setUserInitiator(invocation.getArgument(0));
            friendInvite.setUserAcceptor(invocation.getArgument(1));
            friendInvite.setStatus(FriendStatus.PENDING);

            return friendInvite;
        });

        mvc.perform(post(uri + "/add-friend/{id}", 2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(newInviteDtoJson));

        verify(friendService).makeInvite(userInitiator, userAcceptor);
    }

    @Test
    public void deleteFriend() throws Exception {
        when(friendService.deleteFriend(any(), any())).thenAnswer(invocation -> {
            FriendInvite friendInvite = new FriendInvite();
            friendInvite.setId(1L);
            friendInvite.setUserInitiator(invocation.getArgument(1));
            friendInvite.setUserAcceptor(invocation.getArgument(0));
            friendInvite.setStatus(FriendStatus.PENDING);

            return friendInvite;
        });

        mvc.perform(delete(uri + "/delete-friend/{id}", 2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(mirrorInviteDyoJson));

        verify(friendService).deleteFriend(userInitiator, userAcceptor);
    }

    @Test
    public void acceptInvite() throws Exception {
        when(friendService.acceptFriend(any(), any())).thenAnswer(a -> {
            FriendInvite friendInvite = new FriendInvite();
            friendInvite.setId(1L);
            friendInvite.setUserInitiator(a.getArgument(0));
            friendInvite.setUserAcceptor(a.getArgument(1));
            friendInvite.setStatus(FriendStatus.ADDED);

            return friendInvite;
        });

        mvc.perform(post(uri + "/accept-friend/{id}", 2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(acceptedInviteDtoJson));

        verify(friendService).acceptFriend(userInitiator, userAcceptor);
    }

    @Test
    public void getFriends() throws Exception {
        when(userService.getById(1L)).thenReturn(userInitiator);
        when(friendService.getAllFriends(any(), any())).thenReturn(expectingPageOfFriends);

        mvc.perform(post(uri + "/all-added-friends/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(pageableJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectingPageOfFriendsJson));

        verify(friendService).getAllFriends(userInitiator, pageable);
    }

    @Test
    public void getAllAddedFriends() throws Exception {
        when(userService.getById(1L)).thenReturn(userInitiator);
        when(friendService.getAllFriends(any(), any())).thenReturn(expectingPageOfFriends);

        mvc.perform(get(uri + "/all-added-friends/{id}", 1)
                .param("pageNumber", "0")
                .param("pageSize", "10")
                .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectingPageOfFriendsJson));

        verify(friendService).getAllFriends(userInitiator, pageable);
    }
}