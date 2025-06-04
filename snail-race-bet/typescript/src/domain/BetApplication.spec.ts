import {Podium, Snail} from "./RaceResultProvider";
import {noWinner, Winners} from "./Winners";
import {TestableApplication} from "./TestableApplication";


describe('Bet application give winners for last race', () => {

    let app: TestableApplication;
    beforeEach(() => {
        app = new TestableApplication();
    })

    it('no winner when no bet is placed', async () => {
        const result = await app.getWinnersForLastRace()
        expect(result).toEqual(noWinner)
    });

    it('no winner when no race result is registered', async () => {
        await app.placeBet('lucky boy', Date.parse("2021-01-01T00:00:00Z"), 1, 2, 3);

        const result = await app.getWinnersForLastRace()
        expect(result).toEqual(noWinner)
    });

    describe('winners are bets with pronostic that exactly matches podium', () => {
        it('exact match', async () => {
            await app.placeBet('lucky boy', betTime, 9, 8, 7);

            let fourMinuteLater = betTime + fourMinutes;
            app.simulateRaceResult(fourMinuteLater, Podium.NineHeightSeven());

            const result = await app.getWinnersForLastRace()
            expect(result).toEqual(new Winners(["lucky boy"]));
        });

        it('first place differs', async () => {
            await app.placeBet("looser", betTime, 1, 8, 7);

            let fourMinuteLater = betTime + fourMinutes;
            app.simulateRaceResult(fourMinuteLater, Podium.NineHeightSeven());

            const result = await app.getWinnersForLastRace()
            expect(result).toEqual(noWinner);
        });
    });

    describe('winners are bets placed at least 3 seconds before the race', () => {
        it('before and exactly at the 3 seconds limit are winners', async () => {
            // Given a race at a specific time
            const lastRaceDate = 1234567890;
            app.simulateRaceResult(lastRaceDate, Podium.FiveSixSeven());

            // When bets are placed before and at the three seconds limit
            let fourSecondsBefore = lastRaceDate - fourSeconds;
            await app.placeBet("win before boundary", fourSecondsBefore, 5, 6, 7);
            let threeSecondsBefore = lastRaceDate - threeSeconds;
            await app.placeBet("win on boundary", threeSecondsBefore, 5, 6, 7);

            // Then both bets are winners
            const winners = await app.getWinnersForLastRace();
            expect(winners).toEqual(new Winners(["win before boundary", "win on boundary"]));
        });

        it('after the 3 seconds limit are losers', async () => {
            // Given a race at a specific time
            const lastRaceDate = 1234567890;
            app.simulateRaceResult(lastRaceDate, Podium.FiveSixSeven());

            // When bets are placed just after the 3 seconds limit
            await app.placeBet("lose just after the limit", lastRaceDate - threeSeconds + 1, 5, 6, 7);
            await app.placeBet("lose just before the race date", lastRaceDate - 1, 5, 6, 7);
            await app.placeBet("lose on the race date", lastRaceDate, 5, 6, 7);
            await app.placeBet("lose after the race date", lastRaceDate + 1, 5, 6, 7);

            // Then no bets are winners
            var winners = await app.getWinnersForLastRace();
            expect(winners).toEqual(noWinner);
        });
    });

    describe('bets are valid only for the next race', () => {
        it('all bets that match the podium after the previous date are winners', async () => {
            // Given two races with the same podium offset by 4 minutes
            const previousRace = 1234567890;
            app.simulateRaceResult(previousRace, Podium.FiveSixSeven());
            const lastRaceDate = previousRace + fourMinutes;
            app.simulateRaceResult(lastRaceDate, Podium.FiveSixSeven());

            // When placing bets at different times between the two races
            await app.placeBet("win just after", previousRace + 1, 5, 6, 7);
            await app.placeBet("win between", lastRaceDate - fourSeconds, 5, 6, 7);
            await app.placeBet("win just in time", lastRaceDate - threeSeconds, 5, 6, 7);

            // Then bets are winners
            const winners = app.getWinnersForLastRace();
            expect(await winners).toEqual(new Winners(["win just after", "win between", "win just in time"]));
        })

        it('all bets that match the podium before and at the previous date are not winners', async () => {
            // Given two races with the same podium offset by 4 minutes
            const previousRace = 1234567890;
            const lastRaceDate = previousRace + fourMinutes;
            app.simulateRaceResult(previousRace, Podium.FiveSixSeven());
            app.simulateRaceResult(lastRaceDate, Podium.FiveSixSeven());

            // When placing bets just before and at the first
            await app.placeBet("out dated before the previous race", previousRace - 1, 5, 6, 7);
            await app.placeBet("out dated on the previous race", previousRace, 5, 6, 7);

            // Then no bets are winners
            const winners = await app.getWinnersForLastRace();
            expect(winners).toEqual(noWinner);
        })
    });

    it('races can be registered in any order, not necessarily the chronological one', async () => {
        // Place a bet through betApplication
        const oldBetTime = 567890;
        const oldRaceTime = oldBetTime + fourSeconds;
        const winningBetTime = oldRaceTime + fourMinutes;
        const lastRaceTime = winningBetTime + fourSeconds;
        await app.placeBet("mathieu", oldBetTime, 5, 6, 7);
        await app.placeBet("johan", winningBetTime, 9, 8, 7);

        // Register races in a non-chronological order
        app.simulateRaceResult(lastRaceTime, Podium.NineHeightSeven());
        app.simulateRaceResult(oldRaceTime, Podium.FiveSixSeven());

        // Verify johan is a winner
        expect(await app.getWinnersForLastRace()).toEqual(new Winners(["johan"]));
    })


    const fourMinutes = 4 * 60 * 1000;
    const fourSeconds = 4 * 1000;
    const threeSeconds = 3 * 1000;
    const betTime = Date.parse("2021-01-01T00:00:00Z");

});