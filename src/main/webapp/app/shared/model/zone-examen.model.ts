import { ICentreComposition } from 'app/shared/model/centre-composition.model';

export interface IZoneExamen {
    id?: number;
    codeZoneExamen?: string;
    libelleZoneExamen?: string;
    centreCompositions?: ICentreComposition[];
    centreExamenId?: number;
}

export class ZoneExamen implements IZoneExamen {
    constructor(
        public id?: number,
        public codeZoneExamen?: string,
        public libelleZoneExamen?: string,
        public centreCompositions?: ICentreComposition[],
        public centreExamenId?: number
    ) {}
}
