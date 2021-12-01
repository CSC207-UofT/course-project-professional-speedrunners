package com.boba.bobabuddy.framework.config;

import org.springframework.context.annotation.Configuration;

public class GeneralConfig {
    @Configuration
    public static class SortParam{
        public static final String PRICE = "price";
        public static final String AVG_RATING = "avgRating";
        public static final String UNSORTED = "unsorted";
    }
}
