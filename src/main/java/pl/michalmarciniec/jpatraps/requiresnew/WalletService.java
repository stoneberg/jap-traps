package pl.michalmarciniec.jpatraps.requiresnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.xml.txw2.IllegalSignatureException;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Wallet createWallet(BigDecimal money) {
        Wallet wallet = new Wallet(money);
        walletRepository.save(wallet);
        
        if (wallet.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalSignatureException("Initial amount of money cannot be less than zero");
        }
        
        return wallet;
    }

}
