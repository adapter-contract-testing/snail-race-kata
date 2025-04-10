import {betRepositoryContract} from "./BetRepositoryContract";
import {BetRepositorySimulator} from "./BetRepositorySimulator";

describe('BetRepositorySimulator', () => {
    betRepositoryContract(() => new BetRepositorySimulator());
});