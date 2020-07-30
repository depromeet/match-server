package com.depromeet.match.core;

import com.depromeet.match.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtResolver {
    private static final String KEY = "depromeet-6th-match";
    private static final long EXPIRATION_MINUTES = 15;
    private static final Map<String, Object> HEADERS;

    static {
        HEADERS = new HashMap<>();
        HEADERS.put("typ", "JWT");
        HEADERS.put("alg", SignatureAlgorithm.HS256.name());
    }

    private JwtResolver(){}

    public static String createJwt(
        final long id,
        final String nickName,
        final String profileImageUrl
    ){
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", id);
        claims.put("nickName", nickName);
        claims.put("profileImageUrl", profileImageUrl);

        return Jwts.builder()
            .setHeader(HEADERS)
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(EXPIRATION_MINUTES).toInstant()))
            .signWith(SignatureAlgorithm.HS256, KEY.getBytes())
            .compact();
    }

    public static boolean isExpired(final String jwt) {
        Claims claims = parseJwtToClaims(jwt);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    public static Claims parseJwtToClaims(final String jwt ){
        return Jwts.parser()
            .setSigningKey(KEY.getBytes())
            .parseClaimsJws(jwt)
            .getBody();
    }

    public static User parseJwtToUser(final String jwt){
        Claims claims = parseJwtToClaims(jwt);
        long id = claims.get("id", Long.class);
        String nickName = claims.get("nickName", String.class);
        String profileImageUrl = claims.get("profileImageUrl", String.class);

        return User.UserBuilder.anUser()
            .id(id)
            .nickName(nickName)
            .profileImageUrl(profileImageUrl)
            .build();
    }
}
