import {Bet, BetRepository, PodiumPronostic} from "./BetRepository";

export class BetRepositoryFake implements BetRepository {
    private bets: Bet[] = [];

    async findByDateRange(from: number, to: number): Promise<Array<Bet>> {
        return this.bets.filter(bet => bet.timestamp >= from && bet.timestamp < to)
            .map(bet => new Bet(bet.gambler,
                new PodiumPronostic(bet.pronostic.first, bet.pronostic.second, bet.pronostic.third),
                bet.timestamp));
    }

    async register(bet: Bet): Promise<void> {
        this.bets.push(bet);
    }
}