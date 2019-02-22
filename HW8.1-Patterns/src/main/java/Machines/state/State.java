package Machines.state;

/**
 * @author sergey
 * created on 11.09.18.
 */
public class State {
    private final int value;

    public State(int value) {
        this.value = value;
    }

    State(State state) {
        this.value = state.getValue();
    }

    public int getValue() {
        return value;
    }


}
