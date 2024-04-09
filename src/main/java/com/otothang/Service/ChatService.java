package com.otothang.Service;

import com.otothang.models.Chat;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface ChatService {
    Boolean create(Chat chat);
}
