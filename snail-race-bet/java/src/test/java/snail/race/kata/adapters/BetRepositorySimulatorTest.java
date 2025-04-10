package snail.race.kata.adapters;

import org.junit.jupiter.api.BeforeEach;
import snail.race.kata.domain.BetRepositorySimulator;

public class BetRepositorySimulatorTest extends AbstractBetRepositoryTest {
    @BeforeEach
    void setUp() {
        repository = new BetRepositorySimulator();
    }

}
