package ru.otus.app;

import java.util.concurrent.atomic.AtomicInteger;

public final class Address {
    private final AddressContext id;
    private final MsgWorker worker;

    public Address(AddressContext id, MsgWorker worker) {
        this.id = id;
        this.worker = worker;
    }

    public MsgWorker getWorker() {
        return worker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return id != null ? id.equals(address.id) : address.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public AddressContext getId() {
        return id;
    }
}
