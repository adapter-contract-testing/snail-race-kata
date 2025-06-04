export class Snail {
    constructor(
        public readonly number: number,
        public readonly name: string,
    ) {
    }
}

export class Podium {
    constructor(
        public readonly first: Snail,
        public readonly second: Snail,
        public readonly third: Snail
    ) {
    }

    static NineHeightSeven() {
        return new Podium(new Snail(9, 'Nine'), new Snail(8, 'Height'), new Snail(7, 'Seven'));
    }

    static FiveSixSeven() {
        return new Podium(new Snail(5, 'Speedy'), new Snail(6, 'Naruto'), new Snail(7, 'Michka'));
    }
}

export class SnailRace {
    constructor(
        public readonly raceId:number,
        public readonly timestamp:number,
        public readonly podium:Podium
    ) {
    }

}

function sortFromMoreToLessRecent(a: SnailRace, b:SnailRace) {
    return b.timestamp - a.timestamp
}

export class SnailRaces {
    public readonly races: SnailRace[];

    constructor(races:Array<SnailRace>) {
        this.races = new Array(...races).sort(sortFromMoreToLessRecent)
    }

    static withAdditionalResult(snailRaces: SnailRaces, raceId: number, datetime: number, podium: Podium) {
        return new SnailRaces([...snailRaces.races, new SnailRace(raceId, datetime, podium)]);
    }
}

export interface RaceResultProvider {
    races(): Promise<SnailRaces>
}