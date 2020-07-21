package pl.michalmarciniec.jpatraps.requiresnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final WalletService walletService;

    @Autowired
    public PersonService(PersonRepository personRepository, WalletService walletService) {
        this.personRepository = personRepository;
        this.walletService = walletService;
    }

    @Transactional
    public long createPerson(String name, BigDecimal money) {
        Person person = new Person(name);
        personRepository.save(person);

        try {
        	Wallet wallet = walletService.createWallet(money);
        	person.setWallet(wallet);
		} catch (Exception e) {
			log.error("@catching inner exception=====>{}", e.getMessage());
		}
        
        return person.getId();
    }

}
