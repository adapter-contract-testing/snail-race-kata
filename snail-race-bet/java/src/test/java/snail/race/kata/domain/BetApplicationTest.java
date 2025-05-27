package snail.race.kata.domain;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BetApplicationTest {

    public static final RaceResultProvider.Podium NINE_EIGHT_SEVEN_PODIUM = new RaceResultProvider.Podium(
            new RaceResultProvider.Snail(9, "Turbo"),
            new RaceResultProvider.Snail(8, "Flash"),
            new RaceResultProvider.Snail(7, "Speedy")
    );
    public static final RaceResultProvider.Podium FIVE_SIX_SEVEN_PODIUM = new RaceResultProvider.Podium(
            new RaceResultProvider.Snail(5, "Gonzales"),
            new RaceResultProvider.Snail(6, "Gordon"),
            new RaceResultProvider.Snail(7, "Speedy")
    );
    BetApplication betApplication;
    RaceResultProviderSimulator raceResultProvider = new RaceResultProviderSimulator();

    @BeforeEach
    void setUp() {
        betApplication = new BetApplication(new BetRepositorySimulator(), raceResultProvider);
    }

    @Test
    void no_winner_when_no_bet_is_placed() {
        var winners = betApplication.getWinnersForLastRace();
        assertThat(winners).isEmpty();
    }

    @Nested
    class WinOnlyWhenPronosticExactMatchsPodium {
        @Test
        void exact_match() {
            betApplication.placeBet("me", 1, 9, 8, 7);
            raceResultProvider.simulateRaceResult(33, 1, NINE_EIGHT_SEVEN_PODIUM);
            assertThat(betApplication.getWinnersForLastRace()).containsExactly(new Winner("me"));
        }

        @Test
        void third_place_differs() {
            betApplication.placeBet("me", 1, 9, 8, 7);
            raceResultProvider.simulateRaceResult(33, 1, new RaceResultProvider.Podium(
                    new RaceResultProvider.Snail(9, "Not nine"),
                    new RaceResultProvider.Snail(8, "Flash"),
                    new RaceResultProvider.Snail(4, "Speedy")
            ));
            assertThat(betApplication.getWinnersForLastRace()).isEmpty();
        }
    }

    // test that bets placed less than 3 seconds before the race are not taken into account
    @Test
    void no_winner_when_bet_is_placed_less_than_3_seconds() {
        betApplication.placeBet("me", 1, 9, 8, 7);
        raceResultProvider.simulateRaceResult(2, 3, NINE_EIGHT_SEVEN_PODIUM);
        assertThat(betApplication.getWinnersForLastRace()).isEmpty();
    }

    @Test
    void no_winner_when_the_bet_is_older_than_the_previous_race() {
        // Place a bet through betApplication
        int betDate = 12546;
        betApplication.placeBet("mathieu", betDate, 9, 8, 7);

        // Configure a race that is older than the bet
        raceResultProvider.simulateRaceResult( betDate + 5000 , 3, FIVE_SIX_SEVEN_PODIUM);
        // Configure another race that is newer than the previous race and has a podium that match the pronostic
        raceResultProvider.simulateRaceResult( betDate + 10000 , 3, NINE_EIGHT_SEVEN_PODIUM);

        // Verify there is no winner
        assertThat(betApplication.getWinnersForLastRace()).isEmpty();
    }

    @Disabled
    void no_winners_when_there_is_the_slightest_difference() {
//        betApplication.placeBet("me", 1, 9
    }
}
