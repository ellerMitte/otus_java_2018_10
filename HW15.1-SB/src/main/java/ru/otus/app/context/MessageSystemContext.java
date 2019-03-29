package ru.otus.app.context;

import org.springframework.stereotype.Service;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;

@Service
public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontAddress = new Address("Frontend");
    private Address dbAddress = new Address("DB");

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }
}
