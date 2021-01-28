package com.musicapp.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.web.config.MockSpringSecurityTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Import(MockSpringSecurityTestConfiguration.class)
@WithMockUser
public abstract class AbstractControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    protected ResultActions performGet(String url) throws Exception {
        return performRequest(get(url), null, Collections.emptyMap());
    }

    protected ResultActions performGet(String url, Object contentDto) throws Exception {
        return performRequest(get(url), contentDto, Collections.emptyMap());
    }

    protected ResultActions performGet(String url, Map<String, String> params) throws Exception {
        return performRequest(get(url), null, params);
    }

    protected ResultActions performGet(String url, Object contentDto, Map<String, String> params) throws Exception {
        return performRequest(get(url), contentDto, params);
    }

    protected ResultActions performPost(String url) throws Exception {
        return performRequest(post(url), null, Collections.emptyMap());
    }

    protected ResultActions performPost(String url, Object contentDto) throws Exception {
        return performRequest(post(url), contentDto, Collections.emptyMap());
    }

    protected ResultActions performPost(String url, Map<String, String> params) throws Exception {
        return performRequest(post(url), null, params);
    }

    protected ResultActions performPost(String url, Object contentDto, Map<String, String> params) throws Exception {
        return performRequest(post(url), contentDto, params);
    }

    protected ResultActions performPatch(String url) throws Exception {
        return performRequest(patch(url), null, Collections.emptyMap());
    }

    protected ResultActions performPatch(String url, Object contentDto) throws Exception {
        return performRequest(patch(url), contentDto, Collections.emptyMap());
    }

    protected ResultActions performPatch(String url, Map<String, String> params) throws Exception {
        return performRequest(patch(url), null, params);
    }

    protected ResultActions performPatch(String url, Object contentDto, Map<String, String> params) throws Exception {
        return performRequest(patch(url), contentDto, params);
    }

    protected ResultActions performPut(String url) throws Exception {
        return performRequest(put(url), null, Collections.emptyMap());
    }

    protected ResultActions performPut(String url, Object contentDto) throws Exception {
        return performRequest(put(url), contentDto, Collections.emptyMap());
    }

    protected ResultActions performPut(String url, Map<String, String> params) throws Exception {
        return performRequest(put(url), null, params);
    }

    protected ResultActions performPut(String url, Object contentDto, Map<String, String> params) throws Exception {
        return performRequest(put(url), contentDto, params);
    }

    protected ResultActions performDelete(String url) throws Exception {
        return performRequest(delete(url), null, Collections.emptyMap());
    }

    protected ResultActions performDelete(String url, Object contentDto) throws Exception {
        return performRequest(delete(url), contentDto, Collections.emptyMap());
    }

    protected ResultActions performDelete(String url, Map<String, String> params) throws Exception {
        return performRequest(delete(url), null, params);
    }

    protected ResultActions performDelete(String url, Object contentDto, Map<String, String> params) throws Exception {
        return performRequest(delete(url), contentDto, params);
    }

    protected ResultActions performRequest(MockHttpServletRequestBuilder request,
                                           Object contentDto, Map<String, String> params) throws Exception {

        return mockMvc.perform(request
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(contentDto))
                .params(params.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                entry -> Collections.singletonList(entry.getValue()),
                                (firstValue, secondValue) -> secondValue,
                                LinkedMultiValueMap::new))));
    }
}
