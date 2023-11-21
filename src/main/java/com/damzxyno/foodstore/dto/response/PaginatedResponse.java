package com.damzxyno.foodstore.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
public abstract class PaginatedResponse {
    private long totalItems;
    private long totalPages;
    private long currentPage;
    private long currentItems;
}
