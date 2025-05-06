import pytest

from snail_race_kata.domain.bet_application import BetApplication
from snail_race_kata.domain.bet_repository_simulator import BetRepositorySimulator
from snail_race_kata.domain.race_result_provider import Podium, Snail
from snail_race_kata.domain.race_result_provider_simulator import RaceResultProviderSimulator
from snail_race_kata.domain.winner import Winner

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
        # Place a bet through betApplication
        # Configure race result provider simulator to have the corresponding podium
        # Verify winners
        assert False, "Write the test and implement it"

    def test_no_winners_when_there_is_no_exact_match(self, setup):
        # Place a bet through betApplication
        # Configure race result provider simulator to have another podium
        # Verify there is no winner
        assert False, "Write the test and implement it"

    def test_no_winners_when_bet_is_placed_less_than_3_seconds(self, setup):
        # Place a bet through betApplication
        # Configure race result provider simulator to have the corresponding podium but with a timestamp less than 3 seconds
        # Verify there is no winner
        assert False, "Write the test and implement it"

    def test_no_winner_when_the_bet_is_older_than_the_previous_race(self, setup):
        # Place a bet through betApplication
        # Configure a race that is older than the bet
        # Configure another race that is newer than the previous race and has a podium that match the pronostic
        # Verify there is no winner
        assert False, "Write the test and implement it"
