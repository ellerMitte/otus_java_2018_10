package ru.otus.app;

import java.util.Arrays;

public enum Address {
    FRONTEND(45400, 45500), DBSERVER(60100, 60200);
    private final int minPort;
    private final int maxPort;

    Address(int minPort, int maxPort) {
        this.minPort = minPort;
        this.maxPort = maxPort;
    }

    public int getMinPort() {
        return minPort;
    }

    public int getMaxPort() {
        return maxPort;
    }

    public static Address getAddress(int port) {
        return Arrays.stream(Address.values())
                .filter(address -> port >= address.getMinPort() && port < address.getMaxPort())
                .findFirst()
                .orElse(null);
    }
}
