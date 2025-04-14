from abc import ABC, abstractmethod
from typing import List
from src.domain.bet import Bet


class BetRepository(ABC):

    @abstractmethod
    def register(self, bet: Bet) -> None:
        pass

    @abstractmethod
    def find_by_date_range(self, from_timestamp: int, to_timestamp: int) -> List['Bet']:
        pass