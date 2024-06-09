package com.movies.spacecraft.service.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageResponse<T> {

    private final Integer page;
    private final Integer size;
    private final Integer totalPages;
    private final Long totalElements;
    private final List<T> content = new ArrayList<>();

    public PageResponse(Integer page, Integer size, Integer totalPages, Long totalElements) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public void addContent(List<T> content) {
        this.content.addAll(content);
    }

}
