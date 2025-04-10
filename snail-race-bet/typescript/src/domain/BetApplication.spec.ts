import {RaceResultProviderSimulator} from "./RaceResultProviderSimulator";
import {BetRepositorySimulator} from "./BetRepositorySimulator";
import {BetApplication} from "./BetApplication";
import {Winners} from "./Winners";

describe('BetApplication has', () => {
    let raceResultProvider: RaceResultProviderSimulator;
    let app: BetApplication;

    beforeEach(() => {
        raceResultProvider = new RaceResultProviderSimulator()
        app = new BetApplication(new BetRepositorySimulator(), raceResultProvider);

    });

    it('no winner when no Bet is placed', async () => {
        expect(await app.getWinnersForLastRace()).toEqual(new Winners([]));
    });

    describe("winners when the podium exactly matches the bet", () => {
        it('exact match', () => {
            // Place a bet through betApplication
            // Configure race result provider simulator to have the corresponding podium
            // Verify winners
            fail('Write the test and implement it');
        });

        it('third place differs', () => {
            // Place a bet through betApplication
            // Configure race result provider simulator to have another podium
            // Verify there is no winner
            fail('Write the test and implement it');
        });
    })

    it('no winner when bet is placed less that', () => {
        // Place a bet through betApplication
        // Configure race result provider simulator to have the corresponding podium but with a timestamp less than 3 seconds
        // Verify there is no winner
        fail('Write the test and implement it');
    });

    it('no winner when the bet is older than the previous race', () => {
        // Place a bet through betApplication
        // Configure a race that is older than the bet
        // Configure another race that is newer than the previous race and has a podium that match the pronostic
        // Verify there is no winner
        fail('Write the test and implement it');
    });

});