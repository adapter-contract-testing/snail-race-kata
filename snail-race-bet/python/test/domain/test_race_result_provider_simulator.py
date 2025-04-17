from src.domain.race_result_provider import Podium, Snail
from src.domain.race_result_provider_simulator import RaceResultProviderSimulator
from test.domain.race_result_provider_contract import RaceResultProviderContract


class TestRaceResultProviderSimulator(RaceResultProviderContract):
    def create_race_result_provider(self):
        simulator = RaceResultProviderSimulator()
        simulator.simulate_race_result(
            35678,
            1234567890,
            Podium(Snail(123, "snail1"), Snail(3, "snail2"), Snail(3, "snail3")),
        )
        return simulator
