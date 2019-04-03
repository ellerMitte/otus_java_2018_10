package ru.otus.app;

/**
 * @author Igor on 25.03.19.
 */

public class FrontendService {
    private final Address address;
    private final MsgWorker worker;

    public FrontendService(Address address, MsgWorker worker) {
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
