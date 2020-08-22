package com.depromeet.match.login.controller;

import com.depromeet.match.login.service.KakaoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class LoginController {
    private final String client_id= "aaaaaa";
    private final String redirect_uri= "http://localhost:8000/kakao_oauth";
    @Autowired
    private KakaoAPI kakao;
    /**
    * 프론트로 kakao url 보내줄 컨트롤러 */
    @GetMapping(value="/login")
    public String login() {
        String kakao_login_url = "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
                "&response_type=code";
        System.out.println("url : " +kakao_login_url);
        return kakao_login_url;     //이 url에서 로그인 한다.
    }

    /**
     * 프론트에서 위의 url에서 로그인하면 카카오에서 보내주는 코드값을 받을 컨트롤러
     * */
    @GetMapping(value="/kakao_oauth")
    public String kakao_oauth(@RequestParam("code") String code) {
        System.out.println("code : " + code);
        String access_Token = kakao.getAccessToken(code,client_id, redirect_uri);
        System.out.println("controller access_token : " + access_Token);
        //여기서 jwt로 연결해서 토큰값을 받는다.

        return access_Token;
    }

}
