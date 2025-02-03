import ru.itmo.accounts.Account;
import ru.itmo.accounts.DebitAccountImpl;
import ru.itmo.accounts.DepositAccountImpl;
import ru.itmo.banks.Bank;
import ru.itmo.banks.BankImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.itmo.banks.CentralBank;
import ru.itmo.banks.CentralBankImpl;
import ru.itmo.clients.ClientBuilderImpl;
import ru.itmo.clients.ClientImpl;
import ru.itmo.clients.Director;
import ru.itmo.exceptions.FallingTransactionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTests {
    private BankImpl bankImpl;
    private Account account1;
    private Account account2;
    @BeforeEach
    void setUp() {
        bankImpl = new BankImpl();
        bankImpl.changeInterestRateForTransfer(2.5);
        bankImpl.setCommission(2);
        account1 = new DebitAccountImpl(50000, bankImpl.getInterestRate());
        account2 = new DepositAccountImpl(30000, bankImpl.getInterestRate(), 12);
    }
    @Test
    void testWithdraw() throws Exception {
        account1.withdraw(3000);
        assertEquals(47000, account1.getBalance());
    }

    @Test
    void testDeposit() {
        account2.deposit(3000);
        assertEquals(33000, account2.getBalance());
    }

    @Test
    void testTransfer() throws Exception {
        account1.transfer(account2, 4000);
        assertEquals(46000, account1.getBalance());
        assertEquals(34000, account2.getBalance());
    }

    @Test
    void testTimeAcceleration() {
        Account temp = bankImpl.timeAcceleration(1, account1);
        assertEquals(50003.42465753425, temp.getBalance());
    }

    @Test
    void testMakeInterbankTransfer1() throws Exception {
        BankImpl bankTo = new BankImpl();
        bankImpl.changeInterestRateForTransfer(2.5);
        bankImpl.setCommission(2);
        Account account = new DebitAccountImpl(50000, bankTo.getInterestRate());
        account.transfer(account1, 2000);
        assertEquals(48000, account.getBalance());
    }

    @Test
    void testMakeInterbankTransfer2() throws Exception {
        CentralBank centralBank = new CentralBankImpl();
        Bank senderBank = new BankImpl();
        Bank receiverBank = new BankImpl();
        ClientBuilderImpl clientBuilderImpl = new ClientBuilderImpl("Anna", "Ulitina", account1)
                .setAddress("One Street")
                .setPassportNumber("45657654");
        Director director = new Director(clientBuilderImpl);
        ClientImpl clientImpl = director.construct();
        clientImpl.setFirstName("Anna");
        clientImpl.setSurname("Ulitina");
        clientImpl.setAddress("One Street");
        clientImpl.setPassportNumber("45657654");

        // Создание тестовых счетов в банках отправителя и получателя
        senderBank.addAccountForClient(account1, clientImpl);
        receiverBank.addAccountForClient( account2, clientImpl);

        // Выполнение межбанковского перевода
        centralBank.makeInterbankTransfer(2000, senderBank, receiverBank, clientImpl);
        assertEquals(48000, account1.getBalance()); // Проверка списания средств с банка отправителя
        assertEquals(32000, account2.getBalance()); // Проверка зачисления средств на банк получателя
    }

    @Test
    void testWithdrawAfterDepositTerm() throws Exception {
        Account account = new DepositAccountImpl(30000, bankImpl.getInterestRate(), 12);
        account = bankImpl.timeAcceleration(12, account);
        account.withdraw(1000);
        assertEquals(29000, account.getBalance());
    }

    @Test
    void testWithdrawBeforeDepositTerm() throws Exception {
        Account account = new DepositAccountImpl(30000, bankImpl.getInterestRate(), 12);
        account = bankImpl.timeAcceleration(10, account);
        Account finalAccount = account;
        assertThrows(FallingTransactionException.class, () -> {
            finalAccount.withdraw(1000);
        });
    }
}
