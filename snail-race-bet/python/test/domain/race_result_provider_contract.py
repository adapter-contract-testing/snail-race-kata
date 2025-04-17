from abc import ABC, abstractmethod

from src.domain.race_result_provider import SnailRaces


class RaceResultProviderContract(ABC):
    @abstractmethod
    def create_race_result_provider(self):
        pass

    def test_provide_race_result(self):
        provider = self.create_race_result_provider()

        races_result = provider.races()

        assert isinstance(races_result, SnailRaces)
        assert len(races_result.races) > 0
