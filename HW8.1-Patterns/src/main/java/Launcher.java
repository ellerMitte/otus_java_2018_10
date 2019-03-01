import Exceptions.DepositException;
import Exceptions.WithdrawException;
import Machines.ATM;
import Machines.Banknote;
import department.ATMDepartment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor on 20.02.19.
 */
public class Launcher {

    public static void main(String[] args) {

        ATMDepartment atmDepartment = new ATMDepartment();
        atmDepartment.addATM(ATM.builder()
                .addCassete(Banknote.OneThousand, 1)
                .addCassete(Banknote.OneHundred, 50)
                .addCassete(Banknote.FiveThousand, 10)
                .build());
        atmDepartment.addATM(ATM.builder()
                .addCassete(Banknote.FiveThousand, 100)
                .addCassete(Banknote.FiveHundred, 50)
                .addCassete(Banknote.TwoThousand, 20)
                .addCassete(Banknote.OneThousand, 45)
                .build());
        atmDepartment.printBalance();
        ATM atm1 = atmDepartment.getATM(0);
        ATM atm2 = atmDepartment.getATM(1);
        atm1.readBalance();
        try {
            atm1.withdraw(54000);
        } catch (WithdrawException e) {
            e.printStackTrace();
        }
        atm1.readBalance();
        try {
            atm1.deposit(4000, 2);
        } catch (DepositException e) {
            System.out.println(e.getMessage());
        }
        try {
            atm2.deposit(5000, 2);
        } catch (DepositException e) {
            System.out.println(e.getMessage());
        }
        atm2.readBalance();
        Map<Integer, Integer> money = new HashMap<>();
        money.put(5000, 100);
        money.put(200, 50);
        money.put(50, 300);
        money.put(1000, 10);
        atm2.depositMulti(money);
        try {
            atm2.withdraw(7000);
        } catch (WithdrawException e) {
            System.out.println(e.getMessage());
        }
        atm2.readBalance();

        atmDepartment.printBalance();

        atmDepartment.restoreState();
        System.out.println("после восстановления начального состояния");

        atmDepartment.printBalance();

    }
}
