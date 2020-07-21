package pl.michalmarciniec.jpatraps.requiresnew;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Wallet createWallet(BigDecimal money) {
        Wallet wallet = new Wallet(money);
        walletRepository.save(wallet);
        
        if (wallet.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Initial amount of money cannot be less than zero");
        }
        
        return wallet;
    }

}
