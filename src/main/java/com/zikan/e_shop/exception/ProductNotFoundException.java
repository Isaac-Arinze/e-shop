package com.zikan.e_shop.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String messgae) {

        super(messgae);
    }
}
