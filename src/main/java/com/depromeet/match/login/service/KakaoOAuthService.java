package com.depromeet.match.login.service;

import com.depromeet.match.core.jwt.JwtResolver;
import com.depromeet.match.user.User;
import com.depromeet.match.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class KakaoOAuthService {
    private static final Logger log = LoggerFactory.getLogger(KakaoOAuthService.class);

    private final UserRepository userRepository;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.redirect-url}")
    private String redirectUrl;

    @Value("${kakao.provider.token-url}")
    private String tokenUrl;

    @Value("${kakao.provider.user-url}")
    private String userUrl;

    public KakaoOAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public String jwt(String authorizeCode) {
        String accessToken = getAccessToken(authorizeCode);

        String jwt = "";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final HttpPost post = new HttpPost(userUrl);
            // add header
            post.addHeader("Authorization", "Bearer " + accessToken);
            post.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            User user = getUserFromResponse(client, post);

            userRepository.save(user);
            jwt = JwtResolver.createJwt(user.getId(), user.getNickName(), user.getProfileImageUrl());
            log.info("{}", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jwt;
    }

    private User getUserFromResponse(CloseableHttpClient client, HttpPost post) throws IOException {
        try (final CloseableHttpResponse response = client.execute(post)) {
            final int responseCode = response.getStatusLine().getStatusCode();
            log.info("Sending 'POST' request to URL : {}", userUrl);
            log.info("Response Code : {}", responseCode);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode returnNode = mapper.readTree(response.getEntity().getContent());
            log.info("{}", returnNode);

            long id = returnNode.get("id").asLong();
            JsonNode properties = returnNode.get("properties");
            String nickName = properties.get("nickname").asText();
            String profileImage = properties.get("profile_image").asText();
            String thumbnailImage = properties.get("thumbnail_image").asText();

            return User.UserBuilder.anUser()
                    .id(id)
                    .nickName(nickName)
                    .profileImageUrl(profileImage)
                    .thumbnailImageUrl(thumbnailImage)
                    .build();
        }
    }

    private String getAccessToken(String authorizeCode) {
        String accessToken = "";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()){
            final HttpPost post = new HttpPost(tokenUrl);
            post.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            List<NameValuePair> params = List.of(
                    new BasicNameValuePair("grant_type", "authorization_code"),
                    new BasicNameValuePair("client_id", clientId),
                    new BasicNameValuePair("redirect_uri", redirectUrl),
                    new BasicNameValuePair("code", authorizeCode)
            );
            post.setEntity(new UrlEncodedFormEntity(params));

            accessToken = accessTokenFromResponse(client, post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    private String accessTokenFromResponse(CloseableHttpClient client, HttpPost post) throws IOException {
        String accessToken = "";
        try (final CloseableHttpResponse response = client.execute(post) ) {
            log.info("Sending 'POST' request to URL : {}", tokenUrl);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode returnNode = mapper.readTree(response.getEntity().getContent());
            log.info("{}", returnNode);

            accessToken = returnNode.get("access_token").asText();
        }

        return accessToken;
    }
}
