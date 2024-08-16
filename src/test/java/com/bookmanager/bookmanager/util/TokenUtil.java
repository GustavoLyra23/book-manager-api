package com.bookmanager.bookmanager.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public final class TokenUtil {

    @Value("${security.client-id}")
    private String clientId;

    @Value("${security.client-secret}")
    private String clientSecret;

    private static String username = "gustavo@gmail.com";
    private static String password = "123456";

    public String obtainAccessToken(MockMvc mockMvc) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        ResultActions result = mockMvc
                .perform(post("/oauth2/token")
                        .params(params)
                        .with(httpBasic(clientId, clientSecret))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    public String generateExpiredToken(MockMvc mockMvc) throws Exception {

        String validToken = obtainAccessToken(mockMvc);

        String[] tokenParts = validToken.split("\\.");

        String expiredPayload = "eyJleHAiOjE2MDAwMDAwMDB9";

        return tokenParts[0] + "." + expiredPayload + "." + tokenParts[2];
    }
}


