package snail.race.kata.domain;

import org.junit.jupiter.api.BeforeEach;

public class BetRepositorySimulatorTest extends BetRepositoryContract {
    private BetRepositorySimulator repository;

    @BeforeEach
    void setUp() {
        repository = new BetRepositorySimulator();
    }

    @Override
    protected BetRepository getRepository() {
        return repository;
    }
}
