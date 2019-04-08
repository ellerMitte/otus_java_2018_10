package ru.otus.app;

import ru.otus.channel.MsgWorker;
import ru.otus.messages.Msg;

import java.util.List;

/**
 * @author Igor on 04.04.19.
 */
public interface AddressService {
    void addWorker(Address address, MsgWorker worker);

    List<MsgWorker> getWorkers();

    void deleteWorker(MsgWorker worker);

    void sendMessage(MsgWorker thisWorker, Msg msg);
}
