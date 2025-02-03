package ru.itmo.banks;

import ru.itmo.clients.Client;

public interface CentralBank {
    void addObserver(Bank observer);
    void removeObserver(Bank observer);
    void notifyObservers();
    void makeInterbankTransfer(double amount, Bank senderBank, Bank receiverBank, Client client)
            throws Exception;
}
