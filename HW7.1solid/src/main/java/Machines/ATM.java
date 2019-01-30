package Machines;

import Exceptions.WithdrawException;
import operations.OperationExecutor;
import operations.GetMoney;

import java.util.*;

public class ATM {

    private List<Cassette> cassettes;

    public ATM(List<Cassette> cassettes) {
        this.cassettes = cassettes;
    }

    public void deposit(int value, int count) {
        Cassette cassette = cassettes.stream().filter(cas -> value == cas.getBanknoteFaceValue()).findFirst().get();
        cassette.put(count);
        System.out.println("вы положили " + value * count + " руб.");
    }

    public void withdraw(int sum) throws WithdrawException {
        OperationExecutor executor = new OperationExecutor();
        cassettes.sort((cas1, cas2) -> cas2.getBanknoteFaceValue() - cas1.getBanknoteFaceValue());

        int testSum = sum;
        if (sum > getBalance()) {
            throw new WithdrawException("в банкомате не хватает денег для запрошенной суммы");
        }

        for (Cassette cassete : cassettes) {
            if (testSum <= cassete.getBalance()) {
                executor.addCommand(new GetMoney(cassete, testSum / cassete.getBanknoteFaceValue()));
                testSum = testSum % cassete.getBanknoteFaceValue();
                if (testSum == 0) {
                    break;
                }
            } else {
                executor.addCommand(new GetMoney(cassete, cassete.getCount()));
                testSum = testSum - cassete.getBalance();
            }
        }
        if (testSum == 0) {
            executor.executeCommands();
            System.out.println("выдано " + sum + "руб.");
        } else {
            throw new WithdrawException("нельзя снять такую сумму");
        }
    }

    public int getBalance() {
        return cassettes.stream().mapToInt(cassete -> cassete.getBalance()).sum();
    }

    public void readBalance() {
        System.out.println("в банкомате = " + getBalance() + " р");
    }

    public static void main(String[] args) {
        List<Cassette> cassettes = new ArrayList<>();
        cassettes.add(new Cassette(1000, 1));
        cassettes.add(new Cassette(100, 50));
        cassettes.add(new Cassette(5000, 10));

        ATM atm = new ATM(cassettes);
        atm.readBalance();
        try {
            atm.withdraw(56000);
        } catch (WithdrawException e) {
            e.printStackTrace();
        }
        atm.readBalance();
        atm.deposit(5000, 2);
        atm.readBalance();
        try {
            atm.withdraw(7000);
        } catch (WithdrawException e) {
            e.printStackTrace();
        }
        atm.readBalance();
    }
}
