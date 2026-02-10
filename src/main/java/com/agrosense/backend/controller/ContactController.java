package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ContactMessageRequest;
import com.agrosense.backend.entity.ContactMessage;
import com.agrosense.backend.repository.ContactMessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactMessageRepository contactMessageRepository;

    @PostMapping
    public ResponseEntity<Map<String, Object>> submitContactMessage(@Valid @RequestBody ContactMessageRequest request) {
        ContactMessage message = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .status(ContactMessage.MessageStatus.NEW)
                .build();

        contactMessageRepository.save(message);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Your message has been sent successfully. We'll get back to you soon!"
        ));
    }
}
