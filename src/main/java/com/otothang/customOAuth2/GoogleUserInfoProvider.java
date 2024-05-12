//package com.otothang.customOAuth2;
//
//import com.otothang.models.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//
//@Component
//public class GoogleUserInfoProvider {
//
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public GoogleUserInfoProvider(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public User getGoogleUserInfo(String code) {
//        String tokenEndpoint = "https://www.googleapis.com/oauth2/v4/token";
//        String userInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";
//
//        // Truy vấn mã thông báo truy cập từ mã truy cập
//        String accessToken = requestAccessToken(code, tokenEndpoint);
//
//        // Lấy thông tin người dùng từ mã thông báo truy cập
//        return requestUserInfo(accessToken, userInfoEndpoint);
//    }
//
//    private String requestAccessToken(String code, String tokenEndpoint) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setBasicAuth("83651994583-qc3grregqtlh02lmq29l90tbs44siqt7.apps.googleusercontent.com", "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");
//
//        // Tạo yêu cầu
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("code", code);
//        requestBody.add("grant_type", "authorization_code");
//        requestBody.add("redirect_uri", "http://localhost:8080/oauth2/authorization/google");
//
//        // Gửi yêu cầu
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
//                tokenEndpoint, HttpMethod.POST, request, new ParameterizedTypeReference<Map<String, Object>>() {});
//
//        // Trích xuất mã thông báo truy cập từ phản hồi
//        Map<String, Object> responseBody = response.getBody();
//        if (responseBody != null && responseBody.containsKey("access_token")) {
//            return responseBody.get("access_token").toString();
//        } else {
//            throw new RuntimeException("Failed to obtain access token from authorization code.");
//        }
//    }
//
//    private User requestUserInfo(String accessToken, String userInfoEndpoint) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//
//        // Gửi yêu cầu để lấy thông tin người dùng
//        HttpEntity<Void> request = new HttpEntity<>(headers);
//        ResponseEntity<User> response = restTemplate.exchange(
//                userInfoEndpoint, HttpMethod.GET, request, User.class);
//        return response.getBody();
//    }
//}