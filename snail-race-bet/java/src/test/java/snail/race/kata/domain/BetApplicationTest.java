package snail.race.kata.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BetApplicationTest {



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
            betApplication.placeBet("me", betTime, 9, 8, 7);
            raceResultProvider.registerRaceResult(betTime + THREE_SECONDS, NINE_EIGHT_SEVEN_PODIUM);
            assertThat(betApplication.getWinnersForLastRace()).containsExactly(new Winner("me"));
        }

        @Test
        void third_place_differs() {
            betApplication.placeBet("me", betTime, 9, 8, 7);
            raceResultProvider.registerRaceResult(betTime + THREE_SECONDS, NINE_HEIGHT_FOUR_PODIUM);
            assertThat(betApplication.getWinnersForLastRace()).isEmpty();
        }
    }

    @Test
    void no_winner_when_bet_is_placed_less_than_3_seconds() {
        betApplication.placeBet("me", betTime, 9, 8, 7);
        raceResultProvider.registerRaceResult(betTime + TWO_SECONDS, NINE_EIGHT_SEVEN_PODIUM);
        assertThat(betApplication.getWinnersForLastRace()).isEmpty();
    }

    @Test
    void no_winner_when_the_bet_is_older_than_the_previous_race() {
        // Place a bet through betApplication
        betApplication.placeBet("mathieu", betTime, 9, 8, 7);

        // Configure a race that is older than the bet
        raceResultProvider.registerRaceResult(betTime + FIVE_SECONDS, FIVE_SIX_SEVEN_PODIUM);
        // Configure another race that is newer than the previous race and has a podium that match the pronostic
        raceResultProvider.registerRaceResult(betTime + TEN_SECONDS, NINE_EIGHT_SEVEN_PODIUM);

        // Verify there is no winner
        assertThat(betApplication.getWinnersForLastRace()).isEmpty();
    }

    public static final RaceResultProvider.Podium NINE_EIGHT_SEVEN_PODIUM = new RaceResultProvider.Podium(
            new RaceResultProvider.Snail(9, "Turbo"),
            new RaceResultProvider.Snail(8, "Flash"),
            new RaceResultProvider.Snail(7, "Speedy")
    );
    public static final RaceResultProvider.Podium NINE_HEIGHT_FOUR_PODIUM = new RaceResultProvider.Podium(
            new RaceResultProvider.Snail(9, "Not nine"),
            new RaceResultProvider.Snail(8, "Flash"),
            new RaceResultProvider.Snail(4, "Speedy")
    );

    public static final RaceResultProvider.Podium FIVE_SIX_SEVEN_PODIUM = new RaceResultProvider.Podium(
            new RaceResultProvider.Snail(5, "Gonzales"),
            new RaceResultProvider.Snail(6, "Gordon"),
            new RaceResultProvider.Snail(7, "Speedy")
    );

    public final int TWO_SECONDS = 2;
    public final int THREE_SECONDS = 3;
    public final int FIVE_SECONDS = 5;
    public final int TEN_SECONDS = 10;
    public final int betTime = 123456;
}
