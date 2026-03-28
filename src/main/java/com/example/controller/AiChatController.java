package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiChatController {

    private static final String API_KEY = "sk-2****e44d";
    private static final String DASHCOPE_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, String> body) {
        System.out.println("收到前端消息：" + body.get("message"));

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + API_KEY);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "qwen-turbo");

            Map<String, Object> input = new HashMap<>();
            List<Map<String, String>> messages = new ArrayList<>();

            Map<String, String> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", "你是水韵江苏文旅网AI客服，只回答旅游、预约、价格、行程、景点相关问题，简洁友好。");
            messages.add(systemMsg);

            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", body.get("message"));
            messages.add(userMsg);

            input.put("messages", messages);
            requestBody.put("input", input);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<?> response = restTemplate.postForEntity(DASHCOPE_URL, entity, Map.class);

            @SuppressWarnings("unchecked")
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            if (responseBody == null) {
                return "AI 服务暂时不可用，请稍后再试~";
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> output = (Map<String, Object>) responseBody.get("output");
            if (output == null) {
                return "AI 服务响应异常，请稍后再试~";
            }
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> choices = (List<Map<String, Object>>) output.get("choices");
            if (choices == null || choices.isEmpty()) {
                return "AI 服务响应异常，请稍后再试~";
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            if (message == null) {
                return "AI 服务响应异常，请稍后再试~";
            }
            String result = (String) message.get("content");

            System.out.println("AI 返回结果：" + result);
            return result;

        } catch (Exception e) {
            System.err.println("AI 接口调用失败：");
            e.printStackTrace();
            return "你好！我是水韵江苏旅游AI客服，很高兴为您服务~";
        }
    }
}