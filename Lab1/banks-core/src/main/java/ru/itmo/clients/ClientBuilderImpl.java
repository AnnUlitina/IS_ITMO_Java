package ru.itmo.clients;

import ru.itmo.accounts.Account;
import ru.itmo.accounts.AccountImpl;

/**
 * Builder class for creating instances of {@link ClientImpl}.
 */
public class ClientBuilderImpl implements ClientBuilder {
    private final String firstName;
    private final String surname;
    private String address;
    private String passportNumber;
    private Account account;

    public ClientBuilderImpl(String firstName, String lastName, Account account) {
        this.firstName = firstName;
        this.surname = lastName;
        this.account = account;
    }

    /**
     * Sets the address for the client being built.
     *
     * @param address The address to set.
     * @return The client builder instance.
     */
    @Override
    public ClientBuilderImpl setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Sets the passport number for the client being built.
     *
     * @param passportNumber The passport number to set.
     * @return The client builder instance.
     */
    @Override
    public ClientBuilderImpl setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    /**
     * Sets the account for the client being built.
     *
     * @param account The account to set.
     * @return The client builder instance.
     */
    @Override
    public ClientBuilderImpl setAccount(Account account) {
        this.account = account;
        return this;
    }

    /**
     * Builds and returns a new client instance with the configured properties.
     *
     * @return A new instance of {@link ClientImpl}.
     */
    @Override
    public ClientImpl build() {
        ClientImpl clientImpl = new ClientImpl(firstName, surname);
        clientImpl.setAddress(address);
        clientImpl.setPassportNumber(passportNumber);
        return clientImpl;
    }
}
