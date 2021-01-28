package com.musicapp.service.stub;

import com.musicapp.service.UserStreamService;
import com.musicapp.util.constants.ProfileConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * Заглушка сервиса для взаимодействия с message broker.
 *
 * @author a.nagovicyn
 */
@Service
@Slf4j
@Profile(ProfileConstants.LOCAL)
public class StubUserStreamServiceImpl implements UserStreamService {

    @Override
    public MessageChannel output() {
        log.debug("Local kafka impl is on");
        return (message, timeout) -> {
            log.debug("Message channel is not realised in local profile, stub is activated");
            return true;
        };
    }
}
