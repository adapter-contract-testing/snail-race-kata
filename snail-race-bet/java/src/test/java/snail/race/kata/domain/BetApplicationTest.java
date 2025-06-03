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

    @Nested
    class WinOnlyIfPlacedAtLeast3SecondsBeforeRace {
        @Test
        void before_and_at_the_three_seconds_are_winner() {
            // Given a race at a specific time
            var lastRaceDate = 1234567890;
            raceResultProvider.registerRaceResult(lastRaceDate, FIVE_SIX_SEVEN_PODIUM);

            // When bets are placed before and at the three seconds limit
            betApplication.placeBet("win before boundary", lastRaceDate - THREE_SECONDS, 5, 6, 7);
            betApplication.placeBet("win on boundary", lastRaceDate - THREE_SECONDS, 5, 6, 7);

            // Then both bets are winners
            var winners = betApplication.getWinnersForLastRace();
            assertThat(winners)
                    .extracting(Winner::gambler)
                    .containsExactlyInAnyOrder("win before boundary", "win on boundary");
        }

        @Test
        void after_the_three_seconds_are_looser() {
            // Given a race at a specific time
            var lastRaceDate = 1234567890;
            raceResultProvider.registerRaceResult(lastRaceDate, FIVE_SIX_SEVEN_PODIUM);

            // When bets are placed before and at the three seconds limit
            betApplication.placeBet("lose just after the limit", lastRaceDate - TWO_SECONDS, 5, 6, 7);
            betApplication.placeBet("lose just before the race date", lastRaceDate - ONE_SECONDS, 5, 6, 7);
            betApplication.placeBet("lose on the race date", lastRaceDate, 5, 6, 7);
            betApplication.placeBet("lose after the race date", lastRaceDate + ONE_SECONDS, 5, 6, 7);

            // Then both bets are winners
            var winners = betApplication.getWinnersForLastRace();
            assertThat(winners).isEmpty();
        }
    }


    @Nested
    class BetIsValidOnlyForTheLastRace {
        @Test
        void all_bets_that_match_the_podium_after_the_previous_date_are_winner(){

            // Given two races with the same podium offset by TEN_SECONDS
            var previousRace = 1234567890;
            var lastRaceDate = previousRace + TEN_SECONDS;
            raceResultProvider.registerRaceResult(previousRace, FIVE_SIX_SEVEN_PODIUM);
            raceResultProvider.registerRaceResult(lastRaceDate, FIVE_SIX_SEVEN_PODIUM);

            // When placing bets at different times
            betApplication.placeBet("win just after", previousRace + ONE_SECONDS, 5, 6, 7);
            betApplication.placeBet("win between", lastRaceDate - FIVE_SECONDS, 5, 6, 7);
            betApplication.placeBet("win just in time", lastRaceDate - THREE_SECONDS, 5, 6, 7);

            // Then only the bets placed between the previous and the last are winners
            var winners = betApplication.getWinnersForLastRace();
            assertThat(winners)
                    .extracting(Winner::gambler)
                    .containsExactlyInAnyOrder("win just after", "win between", "win just in time");
        }

        @Test
        void all_bets_that_match_the_podium_before_and_at_the_previous_date_are_not_winner(){

            // Given two races with the same podium offset by TEN_SECONDS
            var previousRace = 1234567890;
            var lastRaceDate = previousRace + TEN_SECONDS;
            raceResultProvider.registerRaceResult(previousRace, FIVE_SIX_SEVEN_PODIUM);
            raceResultProvider.registerRaceResult(lastRaceDate, FIVE_SIX_SEVEN_PODIUM);

            // When placing bets at different times
            betApplication.placeBet("out dated before the previous race", previousRace - ONE_SECONDS, 5, 6, 7);
            betApplication.placeBet("out dated on the previous race", previousRace, 5, 6, 7);

            // Then only the bets placed between the previous and the last are winners
            var winners = betApplication.getWinnersForLastRace();
            assertThat(winners).isEmpty();
        }

    }

    @Test
    void races_can_be_registered_in_any_order_not_necessary_the_chronological_one() {
        // Place a bet through betApplication
        var oldBetTime = 567890;
        var oldRaceTime = oldBetTime + TEN_SECONDS;
        var winningBetTime = oldRaceTime + TEN_SECONDS;
        var lastRaceTime = winningBetTime + TEN_SECONDS;
        betApplication.placeBet("mathieu", oldBetTime, 5, 6, 7);
        betApplication.placeBet("johan", winningBetTime, 9, 8, 7);

        // Register races in a non-chronological order
        raceResultProvider.registerRaceResult(lastRaceTime, NINE_EIGHT_SEVEN_PODIUM);
        raceResultProvider.registerRaceResult(oldRaceTime, FIVE_SIX_SEVEN_PODIUM);

        // Verify johan is a winner
        assertThat(betApplication.getWinnersForLastRace()).containsExactly(new Winner("johan"));
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

    public final int ONE_SECONDS = 1;
    public final int TWO_SECONDS = 2;
    public final int THREE_SECONDS = 3;
    public final int FIVE_SECONDS = 5;
    public final int TEN_SECONDS = 10;
    public final int betTime = 123456;
}
