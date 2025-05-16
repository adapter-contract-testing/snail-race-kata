from dataclasses import dataclass

from snail_race_kata.domain.podium_pronostic import PodiumPronostic


@dataclass
class Bet:
    gambler: str
    pronostic: PodiumPronostic
    timestamp: int
