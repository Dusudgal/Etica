package com.eticaplanner.eticaPlanner.kakao.service;
import com.eticaplanner.eticaPlanner.kakao.Entity.KakaoUserEntity;
import com.eticaplanner.eticaPlanner.kakao.Repository.KakaoUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class KakaoUserService {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final KakaoUserRepository kakaoUserRepository;

    @Autowired
    public KakaoUserService(
            @Value("${kakao.api.key}") String clientId,
            @Value("${kakao.api.secret}") String clientSecret,
            @Value("${kakao.redirect.uri}") String redirectUri,
            KakaoUserRepository kakaoUserRepository) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.kakaoUserRepository = kakaoUserRepository;
    }

    private RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    public String getUserInfo(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        RestTemplate restTemplate = createRestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();

            // JSON 데이터 전체 출력
            System.out.println("Response Body: " + responseBody);

            JSONObject jsonObject = new JSONObject(responseBody);
            
            // Save user info to the database
            saveUserInfo(jsonObject);

            return jsonObject.toString();
        } catch (HttpClientErrorException e) {
            System.err.println("Error fetching user info: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw new RuntimeException("Failed to get user info", e);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            throw new RuntimeException("Unexpected error while getting user info", e);
        }
    }

    public String getAccessToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = createRestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                return jsonObject.getString("access_token");
            } else {
                throw new RuntimeException("Failed to get access token. Status Code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw new RuntimeException("Failed to get access token due to HTTP error", e);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            throw new RuntimeException("Unexpected error while getting access token", e);
        }
    }

    public String getKakaoLoginUrl() {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
    }

    private void saveUserInfo(JSONObject jsonObject) {
        // JSON에서 사용자 정보 추출
        Long kakaoId = jsonObject.getLong("id");
        String nickname = jsonObject.getJSONObject("properties").getString("nickname");
        String email = jsonObject.getJSONObject("kakao_account").optString("email", "");

        // 이메일 값 로그 출력
        System.out.println("추출된 이메일 = " + email);

        // 데이터베이스에서 사용자가 이미 존재하는지 확인
        Optional<KakaoUserEntity> existingUser = kakaoUserRepository.findByKakaoId(kakaoId);

        if (existingUser.isPresent()) {
            // 사용자가 이미 존재하는 경우, 기존 사용자 정보 출력
            System.out.println("이미 존재하는 사용자: " + existingUser.get().getNickname());
        } else {
            // 새로운 KakaoUserEntity 객체를 생성하고 데이터베이스에 저장
            KakaoUserEntity newUser = KakaoUserEntity.builder()
                    .kakaoId(kakaoId)
                    .nickname(nickname)
                    .email(email)
                    .build();
            kakaoUserRepository.save(newUser);
            System.out.println("새로운 사용자 저장됨: " + newUser.getNickname());
        }
    }
}
