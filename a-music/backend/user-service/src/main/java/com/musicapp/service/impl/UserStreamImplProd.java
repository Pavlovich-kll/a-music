package com.musicapp.service.impl;

import com.musicapp.service.UserStreamService;
import com.musicapp.stream.UserStream;
import com.musicapp.util.constants.ProfileConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для взаимодействия с message broker.
 *
 * @author a.nagovicyn
 */
@Service
@EnableBinding(UserStream.class)
@RequiredArgsConstructor
@Profile(ProfileConstants.PRODUCTION)
@Slf4j
public class UserStreamImplProd implements UserStreamService {

    private final UserStream stream;

    @Override
    public MessageChannel output() {
        return stream.output();
    }
}
