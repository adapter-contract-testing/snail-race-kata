package snail.race.kata.adapters;

import org.junit.jupiter.api.BeforeEach;
import snail.race.kata.domain.FakeBetRepository;

public class FakeBetRepositoryTest extends AbstractBetRepositoryTest {
    @BeforeEach
    void setUp() {
        repository = new FakeBetRepository();
    }

}
