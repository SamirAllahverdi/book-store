package com.example.filter;

import com.example.util.PageableFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultFilter {

    private Integer page = PageableFactory.DEFAULT_PAGE;
    private Integer pageSize = PageableFactory.DEFAULT_PAGE_SIZE;
    private String field = PageableFactory.DEFAULT_SORT_FIELD;
    private String dir = PageableFactory.DEFAULT_SORT_DIRECTION;

}
