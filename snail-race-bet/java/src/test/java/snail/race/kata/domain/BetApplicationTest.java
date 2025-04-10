package snail.race.kata.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BetApplicationTest {

    BetApplication betApplication;
    RaceResultProviderSimulator raceResultProvider = new RaceResultProviderSimulator();

    @BeforeEach
    void setUp() {

        betApplication = new BetApplication(
                new BetRepositorySimulator(), // You don't need to interact with the repository to test the entire domain !
                raceResultProvider);
    }

    @Test
    void no_winner_when_no_bet_is_placed() {
        var winners = betApplication.getWinnersForLastRace();
        assertTrue(winners.isEmpty());
    }

    @Nested
    class WinOnlyWhenPronosticExactMatchsPodium {
        @Test
        void exact_match() {
            // Place a bet through betApplication
            // Configure race result provider simulator to have the corresponding podium
            // Verify winners
            Assertions.fail("Write the test and implement it");
        }

        @Test
        void third_place_differs() {
            // Place a bet through betApplication
            // Configure race result provider simulator to have another podium
            // Verify there is no winner
            Assertions.fail("Write the test and implement it");
        }
    }


    @Test
    void no_winner_when_bet_is_placed_less_than_3_seconds() {
        // Place a bet through betApplication
        // Configure race result provider simulator to have the corresponding podium but with a timestamp less than 3 seconds
        // Verify there is no winner
        Assertions.fail("Write tests and implement it");
    }

    @Test
    void no_winner_when_the_bet_is_older_than_the_previous_race() {
        // Place a bet through betApplication
        // Configure a race that is older than the bet
        // Configure another race that is newer than the previous race and has a podium that match the pronostic
        // Verify there is no winner
        Assertions.fail("Write tests and implement it");
    }
}