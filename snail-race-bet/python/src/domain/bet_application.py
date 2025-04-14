from typing import List

from src.domain.bet import Bet
from src.domain.podium_pronostic import PodiumPronostic
from src.domain.race_result_provider import SnailRace
from src.domain.winner import Winner


class BetApplication:
    def __init__(self, bet_repository, race_result_provider):
        self.bet_repository = bet_repository
        self.race_result_provider = race_result_provider

    def place_bet(self, gambler: str, timestamp: int, first: int, second: int, third: int) -> None:
        self.bet_repository.register(Bet(gambler, PodiumPronostic(first, second, third), timestamp))

    def get_winners_for_last_race(self) -> List[Winner]:
        bets = self.bet_repository.find_by_date_range(0, float('inf'))
        races = self.race_result_provider.races()
        winning_bets = self.find_exact_match_bets(bets, races.get_last_race())

        return [Winner(bet.gambler) for bet in winning_bets]

    def find_exact_match_bets(self, bets: List[Bet], race: SnailRace) -> List['Bet']:
        return [
            bet for bet in bets
            if bet.is_in_time_for(race) and bet.bet_is_on(race.podium)
        ]