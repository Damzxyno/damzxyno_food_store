package com.damzxyno.foodstore.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartId implements Serializable {
    private Long productId;
    private Long customerId;
}
