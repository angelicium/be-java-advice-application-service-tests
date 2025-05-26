package com.itm.space.model.response;

public record HttpErrorResponse(int code, String type, String message) {
}
