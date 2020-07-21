package pl.michalmarciniec.jpatraps.requiresnew;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Optional;

import org.assertj.core.data.Percentage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonServiceTest {
    
    private static Logger log = LoggerFactory.getLogger(PersonServiceTest.class);

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void shouldCreatePersonWithInitialAmountOfMoney() {
        // when
        long jeremyId = personService.createPerson("Jeremy", BigDecimal.TEN);

        // then
        Optional<Person> jeremy = personRepository.findById(jeremyId);
        assertThat(jeremy).isPresent();
        Wallet jeremyWallet = jeremy.get().getWallet();
        assertThat(jeremyWallet).isNotNull();
        assertThat(jeremyWallet.getId()).isNotNull();
        assertThat(jeremyWallet.getAmount()).isCloseTo(BigDecimal.TEN, Percentage.withPercentage(0.1D));
    }

    /**
     * 하위 트랜잰션의 exception으로 인해 전체 트랜잰션이 롤백됨
     */
    @Test
    public void shouldNotCreateAnythingWhenTryingToCreatePersonWithNegativeAmountOfMoney() {
        // when
//        assertThatThrownBy(() -> personService.createPerson("Vince", BigDecimal.valueOf(-100.0D)))
//                .isInstanceOf(IllegalStateException.class);
        
        personService.createPerson("Vince", BigDecimal.valueOf(-100.0D));
        
        log.info("@person=========>{}", personRepository.findAll());
        log.info("@wallet=========>{}", walletRepository.findAll());

        // then
        assertThat(personRepository.findAll()).isNotEmpty();
        assertThat(walletRepository.findAll()).isEmpty();
    }

}