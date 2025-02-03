package ru.itmo.clients;

/**
 * Director class responsible for constructing clients using a client builder.
 */
public class Director {
    private final ClientBuilderImpl clientBuilderImpl;

    public Director(ClientBuilderImpl clientBuilderImpl) {
        this.clientBuilderImpl = clientBuilderImpl;
    }

    /**
     * Constructs and returns a client using the configured client builder.
     *
     * @return A new instance of {@link ClientImpl}.
     */
    public ClientImpl construct() {
        return clientBuilderImpl.build();
    }
}
