package com.depromeet.match.jwt;

import static org.assertj.core.api.Assertions.catchThrowable;

import com.depromeet.match.core.jwt.JwtResolver;
import com.depromeet.match.error.IllegalJwtException;
import com.depromeet.match.user.User;
import io.jsonwebtoken.Claims;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(Lifecycle.PER_CLASS)
class JwtTest {

    private static final Logger log = LoggerFactory.getLogger(JwtTest.class);

    private static final long ID = 10000000;
    private static final String NICK_NAME = "기리";
    private static final String PROFILE_IMAGE_URL = "https://something.image.url";

    private User user;

    @BeforeAll
    void setUp() {
        user = User.UserBuilder.anUser()
            .id(ID)
            .nickName(NICK_NAME)
            .profileImageUrl(PROFILE_IMAGE_URL)
            .build();
    }

    @Test
    @DisplayName("JWT_생성")
    void JWT_생성() throws Exception {
        //given
        //when
        String jwt = JwtResolver.createJwt(user.getId(), user.getNickName(), user.getProfileImageUrl());
        log.info(jwt);

        //then
        Assertions.assertThat(jwt).isNotBlank();

        //verify
    }
    
    @Test
    @DisplayName("JWT_파싱_Claims")
    void JWT_파싱_Claims() throws Exception {
        //given
        String jwt = JwtResolver.createJwt(user.getId(), user.getNickName(), user.getProfileImageUrl());

        //when
        Claims claims = JwtResolver.parseJwtToClaims(jwt);
        long id = claims.get("id", Long.class);
        String nickName = claims.get("nickName", String.class);
        String profileImageUrl = claims.get("profileImageUrl", String.class);

        //then
        Assertions.assertThat(id).isEqualTo(ID);
        Assertions.assertThat(nickName).isEqualTo(NICK_NAME);
        Assertions.assertThat(profileImageUrl).isEqualTo(PROFILE_IMAGE_URL);
        
        //verify
    }

    @Test
    @DisplayName("JWT_파싱_User")
    void JWT_파싱_User() throws Exception {
        //given
        String jwt = JwtResolver.createJwt(user.getId(), user.getNickName(), user.getProfileImageUrl());

        //when
        User parsedUser = JwtResolver.parseJwtToUser(jwt);

        //then
        Assertions.assertThat(parsedUser).isEqualTo(user);
        Assertions.assertThat(parsedUser.getId()).isEqualTo(ID);
        Assertions.assertThat(parsedUser.getNickName()).isEqualTo(NICK_NAME);
        Assertions.assertThat(parsedUser.getProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL);

        //verify
    }

    @Test
    @DisplayName("JWT_만료_체킹")
    void JWT_만료_체킹() throws Exception {
        //given
        String jwt = JwtResolver.createJwt(user.getId(), user.getNickName(), user.getProfileImageUrl());

        //when
        boolean expired = JwtResolver.isExpired(jwt);

        //then
        Assertions.assertThat(expired).isFalse();

        //verify
    }

    @Test
    @DisplayName("JWT_변조")
    void JWT_변조() throws Exception  {
        //given
        String jwt = JwtResolver.createJwt(user.getId(), user.getNickName(), user.getProfileImageUrl());
        log.info(jwt);
        String forgeryJwt = jwt.concat("a");
        log.info(forgeryJwt);

        //when
        Throwable thrown = catchThrowable(() -> JwtResolver.parseJwtToClaims(forgeryJwt));

        //then
        Assertions.assertThat(thrown).isInstanceOf(IllegalJwtException.class);

        //verify
    }
}
