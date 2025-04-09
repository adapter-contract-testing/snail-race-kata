import {Bet, BetRepository, PodiumPronostic} from "./BetRepository";

export class BetRepositoryFake implements BetRepository {
    private bets = new Array<Bet>()
    async register(bet: Bet): Promise<void> {
        this.bets.push(bet);
    }

    async findByDateRange(from: number, to: number): Promise<Bet[]> {
        return this.bets
            .filter(b => b.timestamp >= from && b.timestamp < to)
            .map(b => new Bet(
                b.gambler,
                new PodiumPronostic(b.pronostic.first, b.pronostic.second, b.pronostic.third),
                b.timestamp
            ));
    }
}