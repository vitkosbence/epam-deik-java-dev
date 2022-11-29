package com.epam.training.ticketservice.core.account;

import com.epam.training.ticketservice.core.account.model.AccountDto;
import com.epam.training.ticketservice.core.account.persistence.entity.Account;
import com.epam.training.ticketservice.core.account.persistence.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class AccountTest {
    private AccountService underTest;
    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDescribeShouldReturnEmptyOptionalByDefault(){
        Optional<AccountDto> expectedResult = Optional.empty();
        underTest = new AccountServiceImpl(accountRepository);
        Optional<AccountDto> actualResult = underTest.describe();
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testDescribeShouldReturnCorrectAccountDtoAfterSuccessfulLogin(){
        Optional<AccountDto> expectedResult = Optional.of(new AccountDto("Sanyi", Account.UserType.NORMAL));
        BDDMockito.given(accountRepository.findByUsernameAndPassword("Sanyi","test")).willReturn(Optional.of(new Account("Sanyi","test", Account.UserType.NORMAL)));
        underTest = new AccountServiceImpl(accountRepository);
        underTest.logIn("Sanyi","test");
        Optional<AccountDto> actualResult = underTest.describe();
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testDescribeShouldReturnEmptyOptionalAfterUnsuccessfulLogin(){
        Optional<AccountDto> expectedResult = Optional.empty();
        BDDMockito.given(accountRepository.findByUsernameAndPassword("Sanyi","test")).willReturn(Optional.of(new Account("Sanyi","test", Account.UserType.NORMAL)));
        underTest = new AccountServiceImpl(accountRepository);
        underTest.logIn("Sanyi","test2");
        Optional<AccountDto> actualResult = underTest.describe();
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testDescribeShouldReturnEmptyOptionalAfterLogout(){
        Optional<AccountDto> expectedResult = Optional.empty();
        BDDMockito.given(accountRepository.findByUsernameAndPassword("Sanyi","test")).willReturn(Optional.of(new Account("Sanyi","test", Account.UserType.NORMAL)));
        underTest = new AccountServiceImpl(accountRepository);
        underTest.logIn("Sanyi","test");
        underTest.logOut();
        Optional<AccountDto> actualResult = underTest.describe();
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testRegisterShouldSaveObjectToRepository(){
        underTest = new AccountServiceImpl(accountRepository);
        underTest.registerAccount("Sanyi","test");
        Mockito.verify(accountRepository).save(new Account("Sanyi","test", Account.UserType.NORMAL));
    }
}
