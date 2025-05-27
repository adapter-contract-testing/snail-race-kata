package snail.race.kata.domain;

import org.junit.jupiter.api.BeforeEach;

class RaceResultProviderSimulatorTest extends RaceResultProviderContract {

    private RaceResultProviderSimulator raceResultProviderSimulator;

    @BeforeEach
    void setUp() {
        var resultProviderSimulator = new RaceResultProviderSimulator();
        RaceResultProvider.Podium podium = new RaceResultProvider.Podium(
                new RaceResultProvider.Snail(1, "Turbo"),
                new RaceResultProvider.Snail(2, "Flash"),
                new RaceResultProvider.Snail(3, "Speedy"));
        resultProviderSimulator.registerRaceResult(1, podium);
        RaceResultProvider.Podium podium2 = new RaceResultProvider.Podium(
                new RaceResultProvider.Snail(2, "Flash"),
                new RaceResultProvider.Snail(1, "Turbo"),
                new RaceResultProvider.Snail(3, "Speedy"));
        resultProviderSimulator.registerRaceResult(10, podium2);
        raceResultProviderSimulator = resultProviderSimulator;

    }

    @Override
    protected RaceResultProvider raceResultProvider() {
        return raceResultProviderSimulator;
    }
}