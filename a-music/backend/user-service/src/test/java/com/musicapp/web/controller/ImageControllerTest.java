package com.musicapp.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.config.ImageServiceConfig;
import com.musicapp.security.oauth2.Oauth2SuccessHandler;
import com.musicapp.security.oauth2.Oauth2UserServiceImpl;
import com.musicapp.security.oauth2.OidcUserServiceImpl;
import com.musicapp.service.ImageService;
import com.musicapp.service.ImageVerificationService;
import com.musicapp.service.TokenService;
import com.musicapp.service.impl.ImageVerificationServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
@MockBean({TokenService.class, UserDetailsService.class, Oauth2UserServiceImpl.class, OidcUserServiceImpl.class,
        Oauth2SuccessHandler.class, ClientRegistrationRepository.class})
@Import(ImageServiceConfig.class)
@WithMockUser
public class ImageControllerTest {

    private static final String URI = "/images";
    private static final String fileName = "image.jpg";

    @TestConfiguration
    static class Configuration {

        @Bean
        public ImageVerificationService imageVerificationService() {
            return new ImageVerificationServiceImpl();
        }
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ImageVerificationService imageVerificationService;

    @MockBean
    private ImageService imageServiceMock;

    private static MockMultipartFile imageFile;
    private static MockMultipartFile audioFile;

    @BeforeClass
    public static void setUpFiles() {
        imageFile = new MockMultipartFile("file", "image.jpg", "image/jpeg", "some picture".getBytes());
        audioFile = new MockMultipartFile("file", "song.mp4", "audio/mp4", "some song".getBytes());
    }

    @Test
    public void getFileType_whenFileTypeIsJpeg_thenReturnJpeg() {
        assertThat(imageVerificationService.getFileType(imageFile)).isEqualTo(".jpeg");
    }

    @Test
    public void verify_whenFileTypeIsValid_thenReturnTrue() {
        assertThat(imageVerificationService.verify(imageFile)).isEqualTo(true);
    }

    @Test
    public void verify_whenFileTypeIsNotValid_thenReturnFalse() {
        assertThat(imageVerificationService.verify(audioFile)).isEqualTo(false);
    }

    @Test
    public void uploadImage() throws Exception {
        when(imageServiceMock.upload(imageFile)).thenReturn(fileName);

        mvc.perform(MockMvcRequestBuilders.multipart(URI)
                .file(imageFile))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.fileName", is(fileName)));
    }
}