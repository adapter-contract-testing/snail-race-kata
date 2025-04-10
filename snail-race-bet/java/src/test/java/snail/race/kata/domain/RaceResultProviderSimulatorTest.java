package snail.race.kata.domain;

import org.junit.jupiter.api.BeforeEach;

class RaceResultProviderSimulatorTest extends RaceResultProviderContract {

    private RaceResultProviderSimulator raceResultProviderSimulator;

    @BeforeEach
    void setUp() {
        var simulator = new RaceResultProviderSimulator();
        RaceResultProvider.Podium podium = new RaceResultProvider.Podium(
                new RaceResultProvider.Snail(1, "Turbo"),
                new RaceResultProvider.Snail(2, "Flash"),
                new RaceResultProvider.Snail(3, "Speedy"));
        simulator.simulateRaceResult(1, 1, podium);
        raceResultProviderSimulator = simulator;

    }

    @Override
    protected RaceResultProvider raceResultProvider() {
        return raceResultProviderSimulator;
    }
}