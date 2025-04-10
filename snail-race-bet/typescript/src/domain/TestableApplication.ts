import {BetApplication} from "./BetApplication";
import {RaceResultProviderSimulator} from "./RaceResultProviderSimulator";
import {BetRepositorySimulator} from "./BetRepositorySimulator";
import {Podium} from "./RaceResultProvider";

export class TestableApplication {
    private readonly app: BetApplication;
    private readonly raceResultProviderSimulator: RaceResultProviderSimulator;

    constructor() {
        this.raceResultProviderSimulator = new RaceResultProviderSimulator();
        this.app = new BetApplication(new BetRepositorySimulator(), this.raceResultProviderSimulator);
    }

    async getWinnersForLastRace() {
        return this.app.getWinnersForLastRace()
    }

    async placeBet(gambler: string, timestamp: number, first: number, second: number, third: number) {
        return this.app.placeBet(gambler, timestamp, first, second, third)
    }

    simulateRaceResult(date: number, podium: Podium) {
        this.raceResultProviderSimulator.simulateRaceResult(594,date, podium)
    }
}