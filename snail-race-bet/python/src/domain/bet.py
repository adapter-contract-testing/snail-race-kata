from dataclasses import dataclass

from src.domain.podium_pronostic import PodiumPronostic


@dataclass
class Bet:
    gambler: str
    pronostic: PodiumPronostic
    timestamp: int

    def is_in_time_for(self, race):
        return self.timestamp > race.timestamp - 2

    def bet_is_on(self, podium):
        return (
            self.pronostic.first == podium.first.number
            and self.pronostic.second == podium.second.number
            and self.pronostic.third == podium.third.number
        )
