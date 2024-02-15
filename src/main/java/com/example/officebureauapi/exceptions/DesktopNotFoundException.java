package com.example.officebureauapi.exceptions;

import java.util.UUID;

public class DesktopNotFoundException extends RuntimeException {
    public DesktopNotFoundException(String message) {
        super(message);
    }
}
