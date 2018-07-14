package edu.tseidler;

public enum Status {
    ACTIVE() {
        @Override
        Status other() {
            return INACTIVE;
        }
    }, INACTIVE() {
        @Override
        Status other() {
            return ACTIVE;
        }
    };

    abstract Status other();
}
