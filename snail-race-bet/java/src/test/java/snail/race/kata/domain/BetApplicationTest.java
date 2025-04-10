package snail.race.kata.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BetApplicationTest {

    public static final RaceResultProvider.Podium NINE_EIGHT_SEVEN_PODIUM = new RaceResultProvider.Podium(
            new RaceResultProvider.Snail(9, "Turbo"),
            new RaceResultProvider.Snail(8, "Flash"),
            new RaceResultProvider.Snail(7, "Speedy")
    );
    BetApplication betApplication;
    RaceResultProviderSimulator raceResultProvider = new RaceResultProviderSimulator();

    @BeforeEach
    void setUp() {
        betApplication = new BetApplication(new BetRepositorySimulator(), raceResultProvider);
    }

    @Test
    void no_winners_when_no_bet_is_placed() {
        var winners = betApplication.getWinnersForLastRace();
        assertThat(winners).isEmpty();
    }

    @Test
    void winners_when_there_is_an_exact_match() {
        betApplication.placeBet("me", 1, 9, 8, 7);
        raceResultProvider.simulateRaceResult(33, 1, NINE_EIGHT_SEVEN_PODIUM);
        assertThat(betApplication.getWinnersForLastRace()).containsExactly(new Winner("me"));
    }

    // test non exact match, that should result in no winners
    @Test
    void no_winners_when_there_is_no_exact_match() {
        betApplication.placeBet("me", 1, 9, 8, 7);
        raceResultProvider.simulateRaceResult(33, 1, new RaceResultProvider.Podium(
                new RaceResultProvider.Snail(6, "Not nine"),
                new RaceResultProvider.Snail(8, "Flash"),
                new RaceResultProvider.Snail(7, "Speedy")
        ));
        assertThat(betApplication.getWinnersForLastRace()).isEmpty();
    }

    // test that bets placed less than 3 seconds before the race are not taken into account
    @Test
    void no_winners_when_bet_is_placed_less_than_3_seconds() {
        betApplication.placeBet("me", 1, 9, 8, 7);
        raceResultProvider.simulateRaceResult(2, 3, NINE_EIGHT_SEVEN_PODIUM);
        assertThat(betApplication.getWinnersForLastRace()).isEmpty();
    }
    @Disabled
    void no_winners_when_there_is_the_slightest_difference() {
//        betApplication.placeBet("me", 1, 9
    }
}
