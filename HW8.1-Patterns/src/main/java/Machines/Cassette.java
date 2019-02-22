package Machines;

import Exceptions.WithdrawException;
import Machines.state.Memento;
import Machines.state.State;

public class Cassette {
    private final int banknoteFaceValue;
    private int count;
    private final Memento startState;

    Cassette(int banknoteFaceValue, int count) {
        this.banknoteFaceValue = banknoteFaceValue;
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

    public int getBanknoteFaceValue() {
        return banknoteFaceValue;
    }

    int getBalance() {
        return banknoteFaceValue * count;
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
                "banknoteFaceValue=" + banknoteFaceValue +
                ", count=" + count +
                '}';
    }
}
