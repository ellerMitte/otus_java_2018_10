package operations;

import Machines.Cassette;

public class PutMoney implements Operation {

    private final Cassette cassette;
    private final int count;

    public PutMoney(Cassette cassette, int count) {
        this.cassette = cassette;
        this.count = count;
    }

    @Override
    public void execute() {
        cassette.put(count);
        System.out.println("{внесено " + count + " ед. номиналом " + cassette.getBanknoteFaceValue() + " руб.}");
    }
}
