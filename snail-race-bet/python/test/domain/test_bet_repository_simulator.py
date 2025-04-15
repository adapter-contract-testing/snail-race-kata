from src.domain.bet_repository_simulator import BetRepositorySimulator
from test.domain.bet_repository_contract import BetRepositoryContract


class TestBetRepositorySimulator(BetRepositoryContract):
    def get_repository(self):
        return BetRepositorySimulator()

