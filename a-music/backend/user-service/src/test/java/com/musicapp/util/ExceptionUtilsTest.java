package com.musicapp.util;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertSame;

public class ExceptionUtilsTest {

    @Test
    public void whenGetRootCause_thenReturnCorrectThrowable() {
        Random random = new Random();

        Exception root = new Exception();
        Exception head = null;

        for (int i = 0; i < random.nextInt(100); i++) {
            head = new Exception(root);
        }

        assertSame(ExceptionUtils.getRootCause(head), root);
    }
}