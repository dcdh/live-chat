package com.damdamdeo;

public record Message(String name, String data) {
    public Message(final String name, final String data) {
        this.name = name;
        this.data = data;
    }

    public Message(final String name) {
        this(name, null);
    }
}
