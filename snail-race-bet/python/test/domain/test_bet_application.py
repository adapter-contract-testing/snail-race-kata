import pytest

from snail_race_kata.domain.bet_application import BetApplication
from snail_race_kata.domain.bet_repository_simulator import BetRepositorySimulator
from snail_race_kata.domain.race_result_provider import Podium, Snail
from snail_race_kata.domain.race_result_provider_simulator import RaceResultProviderSimulator
from snail_race_kata.domain.winner import Winner

NINE_EIGHT_SEVEN_PODIUM = Podium(Snail(9, "Turbo"), Snail(8, "Flash"), Snail(7, "Speedy"))
NINE_HEIGHT_FOUR_PODIUM = Podium(Snail(9, "Lucky snail"), Snail(8, "Flash"), Snail(4, "Not seven"))
FIVE_SIX_SEVEN_PODIUM = Podium(Snail(5, "Mambo"), Snail(6, "sex and sun"), Snail(7, "wonders"))

betTime = 12621354

TWO_SECONDS = 2
FIVE_SECONDS = 5
TEN_SECONDS = 10


class TestBetApplication:
    @pytest.fixture(autouse=True)
    def setup(self):
        race_result_provider = RaceResultProviderSimulator()
        bet_application = BetApplication(BetRepositorySimulator(), race_result_provider)
        return bet_application, race_result_provider

    def test_no_winners_when_no_bet_is_placed(self, setup):
        bet_application, _ = setup
        winners = bet_application.get_winners_for_last_race()
        assert winners == []

    class TestWinOnlyWhenPronosticExactMatchsPodium:

        def test_exact_match(self, setup):
            bet_application, race_result_provider = setup
            bet_application.place_bet("me", betTime, 9, 8, 7)
            race_result_provider.simulate_race_result(33, betTime, NINE_EIGHT_SEVEN_PODIUM)
            assert bet_application.get_winners_for_last_race() == [Winner("me")]

        def test_third_place_differs(self, setup):
            bet_application, race_result_provider = setup
            bet_application.place_bet("me", betTime, 9, 8, 7)
            race_result_provider.simulate_race_result(33, betTime, NINE_HEIGHT_FOUR_PODIUM)
            assert bet_application.get_winners_for_last_race() == []

    def test_no_winners_when_bet_is_placed_less_than_3_seconds(self, setup):
        bet_application, race_result_provider = setup
        bet_application.place_bet("me", betTime, 9, 8, 7)
        race_result_provider.simulate_race_result(2, betTime + TWO_SECONDS, NINE_EIGHT_SEVEN_PODIUM)
        assert bet_application.get_winners_for_last_race() == []

    def test_no_winner_when_the_bet_is_older_than_the_previous_race(self, setup):
        bet_application, race_result_provider = setup
        # Place a bet through betApplication
        bet_application.place_bet("mathieu", betTime, 9, 8, 7)

        # Configure a race that is older than the bet
        race_result_provider.simulate_race_result(3, betTime + FIVE_SECONDS, FIVE_SIX_SEVEN_PODIUM)
        # Configure another race that is newer than the previous race and has a podium that match the pronostic
        race_result_provider.simulate_race_result(5, betTime + TEN_SECONDS, NINE_EIGHT_SEVEN_PODIUM)

        # Verify there is no winner
        assert bet_application.get_winners_for_last_race() == [];
