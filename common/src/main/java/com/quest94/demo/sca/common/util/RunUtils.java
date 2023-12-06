package com.quest94.demo.sca.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class RunUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunUtils.class);

    public static <T> T withSilent(Supplier<T> supplier) {
        return run(supplier, RunUtils::silent);
    }

    public static <T> T silent(Throwable e) {
        LOGGER.error("RunUtils silent", e);
        return null;
    }

    public static <T> T run(Supplier<T> supplier, Function<Throwable, T> fallbackHandler) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            if (Objects.nonNull(fallbackHandler)) {
                return fallbackHandler.apply(e);
            }
            throw e;
        }
    }

}
