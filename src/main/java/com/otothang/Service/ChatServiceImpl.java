package com.otothang.Service;

import com.otothang.Repository.ChatRepository;
import com.otothang.models.Chat;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Boolean create(Chat chat) {
        try {
            this.chatRepository.save(chat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
