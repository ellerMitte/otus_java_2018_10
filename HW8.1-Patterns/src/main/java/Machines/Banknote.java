package Machines;

/**
 * @author Igor on 01.03.19.
 */
public enum Banknote {
    Ten(10),
    Fifty(50),
    OneHundred(100),
    FiveHundred(500),
    OneThousand(1000),
    TwoThousand(2000),
    FiveThousand(5000);

    private int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
