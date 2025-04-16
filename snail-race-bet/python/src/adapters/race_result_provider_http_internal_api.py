from typing import List

from dataclasses import dataclass


class RaceResultProviderHttpInternalApi:
    @dataclass
    class Snail:
        duration: float
        name: str
        number: int

    @dataclass
    class Race:
        raceId: int
        snails: List['RaceResultProviderHttpInternalApi.Snail']
        timestamp: int

    @dataclass
    class RacesResponse:
        races: List['RaceResultProviderHttpInternalApi.Race']
