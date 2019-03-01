package department;

import Machines.ATM;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor on 19.02.19.
 */

public class ATMDepartment {
    private List<ATM> listATM;

    public ATMDepartment() {
        listATM = new ArrayList<>();
    }

    public void addATM(ATM atm) {
        listATM.add(atm);
    }

    public ATM getATM(int index) {
        return listATM.get(index);
    }

    public int getBalance() {
        return listATM.stream()
                .mapToInt(ATM::getBalance)
                .sum();
    }

    public void printBalance() {
        System.out.println("баланс по всем АТМ = " + getBalance() + " руб.");
    }

    public void restoreState() {
        listATM.forEach(ATM::restore);
    }
}
