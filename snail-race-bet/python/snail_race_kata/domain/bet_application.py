from typing import List

from snail_race_kata.domain.winner import Winner


class BetApplication:
    def __init__(self, bet_repository, race_result_provider):
        self.bet_repository = bet_repository
        self.race_result_provider = race_result_provider

    def place_bet(
        self, gambler: str, timestamp: int, first: int, second: int, third: int
    ) -> None:
        pass

    def get_winners_for_last_race(self) -> List[Winner]:
        pass