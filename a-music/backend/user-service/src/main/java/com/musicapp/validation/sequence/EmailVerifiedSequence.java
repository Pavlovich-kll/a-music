package com.musicapp.validation.sequence;

import com.musicapp.validation.group.EmailVerifiedGroup;

import javax.validation.GroupSequence;

@GroupSequence({EmailFormatSequence.class, EmailVerifiedGroup.class})
public interface EmailVerifiedSequence {
}
