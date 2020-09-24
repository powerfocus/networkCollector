package org.py.networkCollector.model;

public enum Protocol {
    http("http"), https("https");

    private final String name;

    Protocol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String get() {
        return name;
    }

}
