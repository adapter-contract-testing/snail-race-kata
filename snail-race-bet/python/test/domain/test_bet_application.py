import pytest

from src.domain.bet_application import BetApplication
from src.domain.bet_repository_simulator import BetRepositorySimulator
from src.domain.race_result_provider import Podium, Snail
from src.domain.race_result_provider_simulator import RaceResultProviderSimulator
from src.domain.winner import Winner

NINE_EIGHT_SEVEN_PODIUM = Podium(
    Snail(9, "Turbo"), Snail(8, "Flash"), Snail(7, "Speedy")
)


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

    def test_winners_when_there_is_an_exact_match(self, setup):
        bet_application, race_result_provider = setup
        bet_application.place_bet("me", 1, 9, 8, 7)
        race_result_provider.simulate_race_result(33, 1, NINE_EIGHT_SEVEN_PODIUM)
        assert bet_application.get_winners_for_last_race() == [Winner("me")]

    def test_no_winners_when_there_is_no_exact_match(self, setup):
        bet_application, race_result_provider = setup
        bet_application.place_bet("me", 1, 9, 8, 7)
        race_result_provider.simulate_race_result(
            33, 1, Podium(Snail(6, "Not nine"), Snail(8, "Flash"), Snail(7, "Speedy"))
        )
        assert bet_application.get_winners_for_last_race() == []

    def test_no_winners_when_bet_is_placed_less_than_3_seconds(self, setup):
        bet_application, race_result_provider = setup
        bet_application.place_bet("me", 1, 9, 8, 7)
        race_result_provider.simulate_race_result(2, 3, NINE_EIGHT_SEVEN_PODIUM)
        assert bet_application.get_winners_for_last_race() == []
