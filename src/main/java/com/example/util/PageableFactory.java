package com.example.util;

import com.example.filter.DefaultFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableFactory {
    public static final String DEFAULT_SORT_FIELD = "createdAt";
    public static Integer DEFAULT_PAGE = 0;
    public static Integer DEFAULT_PAGE_SIZE = 10;
    public static String DEFAULT_SORT_DIRECTION = "DESC";

    public static Pageable all() {
        return PageRequest.of(0, Integer.MAX_VALUE);
    }

    public static Pageable getPageable(DefaultFilter filter) {
        return PageRequest.of(filter.getPage(), filter.getPageSize(), getSort(filter));
    }
    private static Sort getSort(DefaultFilter filter) {
        return Sort.by(Sort.Direction.fromString(filter.getDir().toUpperCase()), filter.getField());
    }


}
