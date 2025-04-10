import {snailRacesContract} from "./SnailRacesContract";
import {Podium, Snail, SnailRace, SnailRaces} from "./RaceResultProvider";
import {RaceResultProviderSimulator} from "./RaceResultProviderSimulator";

describe('RaceResultProviderSimulator', () => {
    let raceResultSimulator: RaceResultProviderSimulator;
    beforeEach(() => {
        const snailRaces = new SnailRaces([
            new SnailRace(1, Date.parse('2021-01-01'), new Podium(new Snail(1, 'Turbo'), new Snail(2, 'Flash'), new Snail(3, 'Speedy'))),
            new SnailRace(2, Date.parse('2021-01-01'), new Podium(new Snail(4, 'The good one'), new Snail(5, 'The bad one'), new Snail(6, 'The ugly one'))),
        ]);
        raceResultSimulator = new RaceResultProviderSimulator(snailRaces);

    });
    snailRacesContract(() => raceResultSimulator);

    it('should allow for simulation even after instantiation', async () => {
        raceResultSimulator.simulateRaceResult(3, Date.parse('2021-01-02'), new Podium(new Snail(10, 'The winner'), new Snail(11, 'The loser'), new Snail(12, 'The cheater')));
        let races = await raceResultSimulator.races();
        expect(races.races).toHaveLength(3)
    });
});