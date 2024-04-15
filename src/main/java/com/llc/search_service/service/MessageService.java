package com.llc.search_service.service;

import com.llc.search_service.controller.model.response.MessagesResponse;

public interface MessageService {
    MessagesResponse list(Integer userId, Integer page);
}
