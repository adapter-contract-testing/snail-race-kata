import {Bet, BetRepository} from "./BetRepository";

export class BetRepositorySimulator implements BetRepository {
    private bets = new Array<Bet>()
    async register(bet: Bet): Promise<void> {
        this.bets.push(bet);
    }

    async findByDateRange(from: number, to: number): Promise<Bet[]> {
        return this.bets
            .filter(b => b.timestamp >= from && b.timestamp < to)
    }
}