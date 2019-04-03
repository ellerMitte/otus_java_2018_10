package ru.otus.app;

public class DbService {
    private final Address address;
    private final MsgWorker worker;

    public DbService(Address address, MsgWorker worker) {
        this.address = address;
        this.worker = worker;
    }

    public Address getAddress() {
        return address;
    }

    public MsgWorker getWorker() {
        return worker;
    }
}