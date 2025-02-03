import ru.itmo.accounts.Account;
import ru.itmo.accounts.AccountImpl;
import ru.itmo.accounts.DebitAccountImpl;
import ru.itmo.accounts.DepositAccountImpl;
import ru.itmo.banks.BankImpl;
import ru.itmo.clients.ClientImpl;
import ru.itmo.clients.ClientBuilderImpl;
import ru.itmo.clients.Director;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.itmo.transactioins.*;

import static org.junit.jupiter.api.Assertions.*;

public class BankTests {
    private BankImpl bankImpl;
    private Account account1;
    private Account account2;
    private TransactionHistory transactionHistory;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        bankImpl = new BankImpl();
        bankImpl.changeInterestRateForTransfer(5);
        bankImpl.setCommission(2);
        account1 = new DebitAccountImpl(20000, bankImpl.getInterestRate());
        account2 = new DepositAccountImpl(20000, bankImpl.getInterestRate(), 12);
    }
    @Test
    void testAddAccount() {
        bankImpl.addAccount(account1);
        assertEquals(1, bankImpl.getAccounts().size());
        assertEquals(account1, bankImpl.getAccounts().get(0));
    }

    @Test
    void testRemoveAccount() {
        bankImpl.addAccount(account1);
        bankImpl.addAccount(account2);
        assertEquals(2, bankImpl.getAccounts().size());

        bankImpl.removeAccount(account1);
        assertEquals(1, bankImpl.getAccounts().size());
        assertEquals(account2, bankImpl.getAccounts().get(0));
    }

    @Test
    void testBankNotifyClients() {
        ClientBuilderImpl clientBuilderImpl1 = new ClientBuilderImpl("John", "Doe", account1)
                .setAddress("123 Main Street")
                .setPassportNumber("AB1234567");
        Director director1 = new Director(clientBuilderImpl1);
        ClientImpl clientImpl1 = director1.construct();

        ClientBuilderImpl clientBuilderImpl2 = new ClientBuilderImpl("Anna", "Brain", account2)
                .setAddress("345 Main Street")
                .setPassportNumber("SH1236764");
        Director director2 = new Director(clientBuilderImpl2);
        ClientImpl clientImpl2 = director2.construct();

        bankImpl.addObserverClient(clientImpl1);
        bankImpl.addObserverClient(clientImpl2);
        clientImpl1.setAccount(bankImpl, account1);
        clientImpl2.setAccount(bankImpl, account2);
        bankImpl.notifyObserverClients();
        assertEquals(20002.739726027397, account1.getBalance());
        assertEquals(20083.333333333332,account2.getBalance());
    }

    @Test
    public void testDepositCancellation() throws Exception {
        Transaction deposit = new DepositCommandImpl(account1, 500);
        transactionHistory = new TransactionHistory();
        transactionService = new TransactionService(transactionHistory);
        transactionService.executeTransaction(deposit);
        transactionService.cancelLastTransaction();
        assertEquals(20000, account1.getBalance());
    }

    @Test
    public void testWithdrawalCancellation() throws Exception {
        Transaction withdraw = new WithdrawCommandImpl(account1, 500);
        transactionHistory = new TransactionHistory();
        transactionService = new TransactionService(transactionHistory);
        transactionService.executeTransaction(withdraw);
        transactionService.cancelLastTransaction();
        assertEquals(20000, account1.getBalance());
    }
}