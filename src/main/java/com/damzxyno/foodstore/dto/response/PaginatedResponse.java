package com.damzxyno.foodstore.dto.response;

import lombok.*;

/**
 * Generic paginated meta data
 */
@Getter
@AllArgsConstructor
public abstract class PaginatedResponse {
    private long totalItems;
    private long totalPages;
    private long currentPage;
    private long currentItems;
}
