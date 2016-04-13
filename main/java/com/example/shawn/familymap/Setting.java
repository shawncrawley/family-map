package com.example.shawn.familymap;

/**
 * Stores settings
 */
public class Setting {
    private String value;
    private Boolean state;

    public Setting(String value, Boolean state) {
        this.value = value;
        this.state = state;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
