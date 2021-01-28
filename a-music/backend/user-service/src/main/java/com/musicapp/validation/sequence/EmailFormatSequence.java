package com.musicapp.validation.sequence;

import com.musicapp.validation.group.EmailFormatGroup;
import com.musicapp.validation.group.EmailNotBlankGroup;

import javax.validation.GroupSequence;

@GroupSequence({EmailFormatGroup.class, EmailNotBlankGroup.class})
public interface EmailFormatSequence {
}
