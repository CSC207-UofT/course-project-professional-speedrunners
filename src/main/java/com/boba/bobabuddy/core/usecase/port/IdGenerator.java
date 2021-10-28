package com.boba.bobabuddy.core.usecase.port;

/**
 * an adapter interface for injecting Id generator implementations into usecase objects
 */

public interface IdGenerator {
    String generate();
}
