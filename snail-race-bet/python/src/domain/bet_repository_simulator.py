from copy import deepcopy
from typing import List

from src.domain.bet import Bet


class BetRepositorySimulator:
    def __init__(self):
        self.in_memory_bets = []

    def register(self, bet: Bet) -> None:
        self.in_memory_bets.append(bet)

    def find_by_date_range(self, from_timestamp: int, to_timestamp: int) -> List['Bet']:
        return [
            deepcopy(bet) for bet in self.in_memory_bets
            if from_timestamp <= bet.timestamp < to_timestamp
        ]