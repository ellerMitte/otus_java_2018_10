package department;

import Machines.ATM;

/**
 * @author Igor on 19.02.19.
 */
public interface Department {
    void addAtm(ATM atm);
    int getBalance();
    void restoreState();
}
