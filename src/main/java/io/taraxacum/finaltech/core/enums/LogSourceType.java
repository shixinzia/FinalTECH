package io.taraxacum.finaltech.core.enums;

import javax.annotation.Nonnull;

/**
 * TODO It may be refined in the future.
 */
public enum LogSourceType {
    SLIMEFUN_MACHINE("slimefun machine"),

    PLAYER_USE("player use"),
    ;

    private String type;

    private LogSourceType(@Nonnull String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
