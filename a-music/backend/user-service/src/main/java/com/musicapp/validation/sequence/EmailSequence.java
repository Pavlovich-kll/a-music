package com.musicapp.validation.sequence;

import com.musicapp.validation.group.EmailUniqueGroup;

import javax.validation.GroupSequence;

@GroupSequence({EmailFormatSequence.class, EmailUniqueGroup.class})
public interface EmailSequence {
}
