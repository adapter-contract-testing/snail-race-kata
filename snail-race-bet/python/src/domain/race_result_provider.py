from abc import ABC, abstractmethod
from dataclasses import dataclass
from typing import List


@dataclass
class Snail:
    number: int
    name: str


@dataclass
class Podium:
    first: Snail
    second: Snail
    third: Snail


@dataclass
class SnailRace:
    race_id: int
    timestamp: int
    podium: Podium


class SnailRaces:
    races: List[SnailRace]

    def __init__(self):
        self.races = []

    def with_additional_result(self, race_id: int, datetime: int, podium: Podium) -> 'SnailRaces':
        self.races.append(SnailRace(race_id, datetime, podium))
        return self

    def get_last_race(self) -> SnailRace:
        if not self.races:
            return None
        return self.races[0]

class RaceResultProvider(ABC):

    @abstractmethod
    def races(self) -> SnailRaces:
        pass

