package operations;

import Exceptions.WithdrawException;
import Machines.Cassette;

public class GetMoney implements Operation {

    private final Cassette cassette;
    private final int count;

    public GetMoney(Cassette cassette, int count) {
        this.cassette = cassette;
        this.count = count;
    }
    @Override
    public void execute() {
        try {
            cassette.take(count);
            System.out.println("{выдано " + count + " купюр номиналом " + cassette.getBanknoteFaceValue() + " руб.}");
        } catch (WithdrawException e) {
            e.printStackTrace();
        }
    }
}
