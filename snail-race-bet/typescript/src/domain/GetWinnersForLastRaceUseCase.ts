import {BetRepository, PodiumPronostic} from "./BetRepository";
import {Podium, RaceResultProvider} from "./RaceResultProvider";
import {noWinner, Winners} from "./Winners";

function matchExactly(pronostic: PodiumPronostic, podium: Podium) : boolean {
    return pronostic.first === podium.first.number
        && pronostic.second === podium.second.number
        && pronostic.third === podium.third.number;
}

const threeSeconds = 3000

export class GetWinnersForLastRaceUseCase {
    constructor(private betRepository: BetRepository, private raceResultProvider: RaceResultProvider) {

    }

    async getWinnersForLastRace() {
        const results = await this.raceResultProvider.races();
        if(results.races.length ===0 ){
            return noWinner;
        }
        let validBetDateFrom = 0
        if (results.races.length > 1){
            validBetDateFrom = results.races[1].timestamp + 1;
        }
        const lastRace = results.races[0];

        const bets = await this.betRepository.findByDateRange(
            validBetDateFrom,
            lastRace.timestamp)

        return new Winners(bets
            .filter(bet => matchExactly(bet.pronostic, lastRace.podium))
            .filter(bet => bet.timestamp <= lastRace.timestamp - threeSeconds)
            .map(bet => bet.gambler))
    }
}