package com.llc.search_service.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llc.search_service.controller.model.response.MessagesResponse;
import com.llc.search_service.entity.Message;
import com.llc.search_service.mapper.MessageMapper;
import com.llc.search_service.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Override
    public MessagesResponse list(Integer userId, Integer page) {

        // 分页查询消息列表
        Page<Message> ppage = new Page<>(page, 10);


        //IPage<Message> ipage = new Page<>(page, 10);
        // 需要查询是用户的消息

        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getUserId, userId).or().isNull(Message::getUserId).orderByDesc(Message::getCreatedAt);

        IPage<Message> messagePage = messageMapper.selectPage(ppage, queryWrapper);
        List<Message> messages = messagePage.getRecords();

        MessagesResponse messagesResponse = new MessagesResponse();
        messagesResponse.setTotal(messagePage.getTotal());

        List<MessagesResponse.Message> messageList = new ArrayList<>();
        for (Message message : messages) {
            MessagesResponse.Message mr = new MessagesResponse.Message();
            BeanUtils.copyProperties(message, mr);
            messageList.add(mr);
        }
        messagesResponse.setMessages(messageList);
        return messagesResponse;

    }
}
