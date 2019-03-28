package ru.otus.messageSystem.messages;

import ru.otus.messageSystem.FrontendService;
import ru.otus.messageSystem.entity.Address;
import ru.otus.messageSystem.entity.Addressee;
import ru.otus.messageSystem.entity.Message;

public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}