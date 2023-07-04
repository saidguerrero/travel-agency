package com.devas.travel.agency.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PaginatedObjectDTO<T> {

    private int pageNumber;

    private int rowsOfPage;

    private long total;

    private List<T> data;

}
