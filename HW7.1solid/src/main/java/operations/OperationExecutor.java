package operations;

import java.util.ArrayList;
import java.util.List;

public class OperationExecutor {

    private final List<Operation> commands = new ArrayList<>();

    public void addCommand(Operation command) {
        commands.add(command);
    }

    public void executeCommands() {
        commands.forEach(cmd -> {
            cmd.execute();
        });
    }
}
