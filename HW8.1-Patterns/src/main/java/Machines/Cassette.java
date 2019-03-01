package Machines;

import Exceptions.WithdrawException;
import Machines.state.Memento;
import Machines.state.State;

public class Cassette {
    private final Banknote banknote;
    private int count;
    private final Memento startState;

    Cassette(Banknote banknote, int count) {
        this.banknote = banknote;
        this.count = count;
        this.startState = saveStartState(new State(count));
    }

    private Memento saveStartState(State state) {
        return new Memento(state);
    }

    void restoreStartState() {
        this.count = startState.getState().getValue();
    }

    int getCount() {
        return count;
    }

    Banknote getBanknote() {
        return banknote;
    }

    public int getBanknoteNominal() {
        return banknote.getNominal();
    }

    int getBalance() {
        return banknote.getNominal() * count;
    }

    public void put(int count) {
        this.count += count;
    }

    public void take(int count) throws WithdrawException {
        if (this.count >= count) {
            this.count -= count;
        } else {
            throw new WithdrawException("недостаточно купюр в кассете");
        }
    }

    @Override
    public String toString() {
        return "ATM cassette{" +
                "banknote=" + banknote.getNominal() +
                ", count=" + count +
                '}';
    }
}
