package com.example.officebureauapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DesktopType {
    SINGLE_BUREAU("Single bureau", 1),
    LANDSCAPE("Landscape", 6),
    MEETING_ROOM("Meeting room", 8),
    CONFERENCE_ROOM("Conference room", 12),
    PRIVATE_OFFICE("Private office", 2),
    BREAKOUT_AREA("Breakout area", 4);

    public static final String INVALID_DESKTOP_TYPE = "Desktop.Type.Invalid";

    @Getter
    @JsonValue
    private final String displayName;

    @Getter
    private final int capacity;

    @JsonCreator
    public static DesktopType fromDisplayName(String displayName) {
        for (DesktopType type : values()) {
            if (type.displayName.equals(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException(INVALID_DESKTOP_TYPE + displayName);
    }
}
