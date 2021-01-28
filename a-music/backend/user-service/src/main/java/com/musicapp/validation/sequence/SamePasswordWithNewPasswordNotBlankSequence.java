package com.musicapp.validation.sequence;

import com.musicapp.validation.group.NewPasswordNotBlankGroup;
import com.musicapp.validation.group.PasswordSizeGroup;
import com.musicapp.validation.group.SamePasswordGroup;

import javax.validation.GroupSequence;

@GroupSequence({NewPasswordNotBlankGroup.class, PasswordSizeGroup.class, SamePasswordGroup.class})
public interface SamePasswordWithNewPasswordNotBlankSequence {
}
