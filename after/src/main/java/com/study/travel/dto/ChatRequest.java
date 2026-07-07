package com.study.travel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatRequest {
    @NotBlank(message = "消息不能为空")
    private String message;

    /** 用户自定义的 DeepSeek API Key（可选），不传则使用服务端配置的 key */
    private String apiKey;
}
