import { Moment } from 'moment';

export interface ISurveille {
    id?: number;
    dateDebutSurveillance?: Moment;
    dateFinSurveillance?: Moment;
    membreJuryId?: number;
    salleCompositionId?: number;
}

export class Surveille implements ISurveille {
    constructor(
        public id?: number,
        public dateDebutSurveillance?: Moment,
        public dateFinSurveillance?: Moment,
        public membreJuryId?: number,
        public salleCompositionId?: number
    ) {}
}
