package com.boba.bobabuddy.framework.converter;

import com.boba.bobabuddy.framework.config.GeneralConfig.SortParam;
import org.springframework.data.domain.Sort;

import java.net.MalformedURLException;

// Builder in name, not actually a builder pattern
public class SortQueryBuilder {

    public static Sort buildSort(String param){
        switch (param){
            case SortParam.PRICE: return Sort.by(SortParam.PRICE).ascending();
            case SortParam.AVG_RATING: return Sort.by(SortParam.AVG_RATING).descending();
            case SortParam.UNSORTED: return Sort.unsorted();
        }
        throw new IllegalArgumentException("Cannot recognize sort parameter");
    }
}
