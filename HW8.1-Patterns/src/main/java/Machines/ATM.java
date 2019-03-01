package Machines;

import Exceptions.DepositException;
import Exceptions.WithdrawException;
import operations.GetMoney;
import operations.OperationExecutor;
import operations.PutMoney;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ATM {

    private Map<Integer, Cassette> cassettes;

    private ATM(Map<Integer, Cassette> cassettes) {
        this.cassettes = cassettes;
    }

    public void deposit(int value, int count) throws DepositException {
        try {
            cassettes.get(value).put(count);
            System.out.println("вы положили " + value * count + " руб.");
        } catch (NullPointerException e) {
            throw new DepositException("банкомат не принимает купюры такого номинала " + value + " руб.");
        }
    }

    public void depositMulti(Map<Integer, Integer> money) {
        OperationExecutor executor = new OperationExecutor();
        money.forEach((key, value) -> {
            Cassette cassette = cassettes.get(key);
            if (cassette != null) {
                executor.addCommand(new PutMoney(cassettes.get(key), value));
            } else {
                System.out.println("возвращено " + value + " купюр номиналом " + key);
            }
        });
        executor.executeCommands();
    }

    public void withdraw(int sum) throws WithdrawException {
        OperationExecutor executor = new OperationExecutor();
        List<Cassette> cas = cassettes.entrySet().stream()
                .map(Map.Entry::getValue)
                .sorted((cas1, cas2) -> cas2.getBanknoteFaceValue() - cas1.getBanknoteFaceValue())
                .collect(Collectors.toCollection(ArrayList::new));

        int testSum = sum;
        if (sum > getBalance()) {
            throw new WithdrawException("в банкомате не хватает денег для запрошенной суммы в " + sum + " руб.");
        }

        for (Cassette cassete : cas) {
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
            System.out.println("выдано " + sum + " руб.");
        } else {
            throw new WithdrawException("невозможно снять сумму в " + sum + " руб.");
        }
    }

    public int getBalance() {
        return cassettes.entrySet().stream()
                .mapToInt(value -> value.getValue().getBalance()).sum();
    }

    public void readBalance() {
        System.out.println("в банкомате " + getBalance() + " руб.");
    }

    public void restore() {
        cassettes.forEach((key, value) -> value.restoreStartState());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Map<Integer, Cassette> cassettes;

        private Builder() {
            cassettes = new HashMap<>();
        }

        public Builder addCassete(Banknote banknote, int count) {
            Cassette cassette = new Cassette(banknote, count);
            cassettes.put(cassette.getBanknoteFaceValue(), cassette);
            return this;
        }

        public ATM build() {
            return new ATM(cassettes);
        }
    }
}
