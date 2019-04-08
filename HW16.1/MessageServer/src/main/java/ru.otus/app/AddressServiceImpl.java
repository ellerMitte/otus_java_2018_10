package ru.otus.app;

import ru.otus.channel.MsgWorker;
import ru.otus.messages.Msg;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class AddressServiceImpl implements AddressService {
    private final Map<Address, List<MsgWorker>> workers;

    public AddressServiceImpl() {
        workers = new HashMap<>();
        workers.put(Address.FRONTEND, new CopyOnWriteArrayList<>());
        workers.put(Address.DBSERVER, new CopyOnWriteArrayList<>());
    }

    public void addWorker(Address address, MsgWorker worker) {
        workers.get(address).add(worker);
    }

    @Override
    public List<MsgWorker> getWorkers() {
        List<MsgWorker> msgWorkers = new ArrayList<>();
        workers.forEach((address, msgWorkersList) -> msgWorkers.addAll(msgWorkersList));
        return msgWorkers;
    }

    @Override
    public void deleteWorker(MsgWorker worker) {
        worker.close();
        workers.forEach((address, msgWorkers) -> msgWorkers.remove(worker));
    }

    @Override
    public void sendMessage(MsgWorker thisWorker, Msg msg) {
        if (msg.getFrom() == Address.FRONTEND && msg.getTo() == Address.DBSERVER) {
            workers.get(Address.DBSERVER).stream()
                    .min(Comparator.comparingInt(MsgWorker::getInputSize))
                    .ifPresent(msgWorker -> msgWorker.send(msg));
        } else {
            workers.get(msg.getTo()).forEach(msgWorker -> {
                if (msgWorker != thisWorker) {
                    msgWorker.send(msg);
                }
            });
        }
    }
}
