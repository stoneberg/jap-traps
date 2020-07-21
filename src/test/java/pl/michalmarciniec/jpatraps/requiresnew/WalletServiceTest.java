package pl.michalmarciniec.jpatraps.requiresnew;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WalletServiceTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletService walletService;

    /**
     * 서로 다른 트랜잰션으로 분리(T1,T2)된 메서드의 결과 전달(passing entity)는 DB로 전파(propagation)되지 않는다.
     */
    @Test
    public void shouldCreateAndAddEmptyWalletToPerson() {
        // given (T1)
        Person margaret = personRepository.save(new Person("Margaret"));

        // when (T2)
        long walletId = walletService.createWalletAndAttachToPerson(margaret).getId();

        // then
        Optional<Wallet> dbWallet = walletRepository.findById(walletId);
        Assertions.assertThat(dbWallet).isPresent();

        Optional<Person> dbMargaret = personRepository.findById(margaret.getId());
        Assertions.assertThat(dbMargaret).isPresent();

        Wallet margaretWallet = dbMargaret.get().getWallet();
        assertThat(margaretWallet).isNotNull(); // <== is null, person.setWallet() 이 영속화되지 않음
        assertThat(margaretWallet.getId()).isNotNull();
        assertThat(margaretWallet.getAmount()).isZero();
    }

}