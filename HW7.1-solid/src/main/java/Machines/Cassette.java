package Machines;

import Exceptions.WithdrawException;

public class Cassette {
    private final int banknoteFaceValue;
    private int count;

    public Cassette(int banknoteFaceValue, int count) {
        this.banknoteFaceValue = banknoteFaceValue;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getBanknoteFaceValue() {
        return banknoteFaceValue;
    }

    public int getBalance() {
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
        return "Machines.Cassette{" +
                "banknoteFaceValue=" + banknoteFaceValue +
                ", count=" + count +
                '}';
    }
}
