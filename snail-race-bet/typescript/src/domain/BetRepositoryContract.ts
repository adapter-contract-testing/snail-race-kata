import {Bet, BetRepository, PodiumPronostic} from "./BetRepository";

export function betRepositoryContract(repoProvider: () => BetRepository) {
    test('register a bets', async () => {
        const repository = repoProvider();
        await repository.register(new Bet("me", new PodiumPronostic(2, 4, 6), Date.parse("2021-01-01T00:00:00Z")));
        const result = await repository.findByDateRange(Date.parse("2021-01-01T00:00:00Z"), Date.parse("2021-01-02T00:00:00Z"));
        expect(result).toEqual([new Bet("me", new PodiumPronostic(2, 4, 6), Date.parse("2021-01-01T00:00:00Z"))]);
    })

    async function registerBetAtTimestamp(repository: BetRepository, timestamp: number) {
        const bet = new Bet('mathieu', new PodiumPronostic(1, 2, 3), timestamp);
        await repository.register(bet)
        return bet;
    }

    test('retrieve only bets inside the time range', async () => {
        const repository = repoProvider();
        const from = Date.parse("2021-01-01T00:00:01Z")
        const to = Date.parse("2021-01-02T00:00:00Z")
        const betBeforeFrom = await registerBetAtTimestamp(repository, from - 1);
        const betOnFrom = await registerBetAtTimestamp(repository, from);
        const betAfterFrom = await registerBetAtTimestamp(repository, from + 1);
        const betBeforeEnd = await registerBetAtTimestamp(repository, to - 1);
        const betOnEnd = await registerBetAtTimestamp(repository, to);
        const betAfterEnd = await registerBetAtTimestamp(repository, to + 1);

        const bets = await repository.findByDateRange(from, to);
        expect(bets).toEqual([betOnFrom, betAfterFrom, betBeforeEnd])
    })

    test('the registered bet is not the same instance as the one retrieved', async () => {
        const repository = repoProvider();
        const bet = new Bet("me", new PodiumPronostic(2, 4, 6), 123);
        await repository.register(bet);

        const result = await repository.findByDateRange(0, 456);

        expect(result[0]).not.toBe(bet);
    })

}