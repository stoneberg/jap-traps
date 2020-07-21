package pl.michalmarciniec.jpatraps.requiresnew;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Wallet {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;

    public Wallet() {
        this.amount = BigDecimal.ZERO;
    }

    public Wallet(BigDecimal amount) {
        this.amount = amount;
    }

}
