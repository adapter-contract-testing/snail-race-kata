from abc import ABC, abstractmethod
from copy import deepcopy

from src.domain.bet import Bet
from src.domain.podium_pronostic import PodiumPronostic


class BetRepositoryContract(ABC):
    @abstractmethod
    def get_repository(self):
        pass

    def test_register_a_bet(self):
        repository = self.get_repository()
        repository.register(Bet("Mathieu", PodiumPronostic(20, 12, 4), 12345))

        bets = repository.find_by_date_range(12345, 12346)
        assert len(bets) == 1
        assert bets[0] == Bet("Mathieu", PodiumPronostic(20, 12, 4), 12345)

    def test_retrieve_only_bets_inside_the_time_range(self):
        repository = self.get_repository()
        from_timestamp = 12346
        to_timestamp = 12370

        _bet_before_from = self.register_bet_at_timestamp(
            repository, from_timestamp - 1
        )
        bet_on_from = self.register_bet_at_timestamp(repository, from_timestamp)
        bet_after_from = self.register_bet_at_timestamp(repository, from_timestamp + 1)
        bet_before_to = self.register_bet_at_timestamp(repository, to_timestamp - 1)
        _bet_on_to = self.register_bet_at_timestamp(repository, to_timestamp)
        _bet_after_to = self.register_bet_at_timestamp(repository, to_timestamp + 1)

        bets = repository.find_by_date_range(from_timestamp, to_timestamp)

        assert bets == [bet_on_from, bet_after_from, bet_before_to]

    def test_the_registered_bet_is_not_the_same_instance_as_the_one_retrieved(self):
        repository = self.get_repository()
        bet = Bet("Mathieu", PodiumPronostic(20, 12, 4), 123)
        repository.register(bet)

        bets = repository.find_by_date_range(0, 456)
        assert bets[0] is not bet

    def register_bet_at_timestamp(self, repository, timestamp):
        bet = Bet("Mathieu", PodiumPronostic(20, 12, 4), timestamp)
        repository.register(bet)
        return deepcopy(bet)
