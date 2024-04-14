package ru.filini.expensetrackerservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.filini.expensetrackerservice.model.Transaction;
import ru.filini.expensetrackerservice.repository.ExpenseLimitRepository;
import ru.filini.expensetrackerservice.repository.TransactionRepository;
import ru.filini.expensetrackerservice.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class ExpenseTrackerServiceTests {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ExpenseLimitRepository expenseLimitRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTransactions() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, new BigDecimal("100.00"), "USD", "Expense", LocalDate.now().atStartOfDay(), false, null, null));
        transactions.add(new Transaction(2L, new BigDecimal("50.00"), "EUR", "Income", LocalDate.now().atStartOfDay(), false, null, null));
        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        List<Transaction> result = transactionService.getAllTransactions();

        // Assert
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testSaveTransaction() {
        // Arrange
        Transaction transaction = new Transaction(1L, new BigDecimal("100.00"), "USD", "Expense", LocalDate.now().atStartOfDay(), false, null, null);

        // Act
        transactionService.saveTransaction(transaction);

        // Assert
        verify(transactionRepository, times(1)).save(transaction);
    }


}