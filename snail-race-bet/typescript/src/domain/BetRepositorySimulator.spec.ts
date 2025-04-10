import {betRepositoryContract} from "./BetRepositoryContract";
import {BetRepositorySimulator} from "./BetRepositorySimulator";

describe('BetRepositorySimulator', () => {
    let repository: BetRepositorySimulator;
    beforeEach(() => {
        repository = new BetRepositorySimulator();
    });

    betRepositoryContract(() => repository)
});