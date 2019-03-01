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

    private Map<Banknote, Cassette> cassettes;

    private ATM(Map<Banknote, Cassette> cassettes) {
        this.cassettes = cassettes;
    }

    public void deposit(Banknote banknote, int count) throws DepositException {
        try {
            cassettes.get(banknote).put(count);
            System.out.println("вы положили " + banknote.getNominal() * count + " руб.");
        } catch (NullPointerException e) {
            throw new DepositException("банкомат не принимает купюры номиналом " + banknote.getNominal() + " руб.");
        }
    }

    public void depositMulti(Map<Banknote, Integer> money) {
        OperationExecutor executor = new OperationExecutor();
        money.forEach((key, value) -> {
            Cassette cassette = cassettes.get(key);
            if (cassette != null) {
                executor.addCommand(new PutMoney(cassettes.get(key), value));
            } else {
                System.out.println("возвращено " + value + " купюр номиналом " + key.getNominal() + " руб.");
            }
        });
        executor.executeCommands();
    }

    public void withdraw(int sum) throws WithdrawException {
        OperationExecutor executor = new OperationExecutor();
        List<Cassette> cas = cassettes.entrySet().stream()
                .map(Map.Entry::getValue)
                .sorted((cas1, cas2) -> cas2.getBanknoteNominal() - cas1.getBanknoteNominal())
                .collect(Collectors.toCollection(ArrayList::new));

        int testSum = sum;
        if (sum > getBalance()) {
            throw new WithdrawException("в банкомате не хватает денег для запрошенной суммы в " + sum + " руб.");
        }

        for (Cassette cassete : cas) {
            if (testSum != 0) {
                int count = Math.min(testSum / cassete.getBanknoteNominal(), cassete.getCount());
                executor.addCommand(new GetMoney(cassete, count));
                testSum = testSum - count * cassete.getBanknoteNominal();
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

    public void printBalance() {
        System.out.println("в банкомате " + getBalance() + " руб.");
    }

    public void restore() {
        cassettes.forEach((key, value) -> value.restoreStartState());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Map<Banknote, Cassette> cassettes;

        private Builder() {
            cassettes = new HashMap<>();
        }

        public Builder addCassete(Banknote banknote, int count) {
            Cassette cassette = new Cassette(banknote, count);
            cassettes.put(cassette.getBanknote(), cassette);
            return this;
        }

        public ATM build() {
            return new ATM(cassettes);
        }
    }
}
