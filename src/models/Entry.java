package models;

public class Entry {
    String key;
    Business value;

    Entry(String key, Business value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Business getValue() {
        return value;
    }
}

