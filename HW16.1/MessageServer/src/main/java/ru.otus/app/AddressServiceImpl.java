package ru.otus.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class AddressServiceImpl implements AddressService {
    private final Map<Address, List<MsgWorker>> workers;

    public AddressServiceImpl() {
        workers = new HashMap<>();
        workers.put(Address.FRONTEND, new CopyOnWriteArrayList<>());
        workers.put(Address.DBSERVER, new CopyOnWriteArrayList<>());
    }

    @Override
    public MsgWorker getWorker(Address address) {
        List<MsgWorker> workerList = workers.get(address);
        if (workerList.size() > 0) {
            return workerList.get(0);
        }
        return null;
    }

    @Override
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
}
