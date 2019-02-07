import { Moment } from 'moment';

export interface IAbsence {
    id?: number;
    motifAbsence?: string;
    dateAbsence?: Moment;
    inscriptionId?: number;
    epreuveSpecialiteOptionId?: number;
    etapeExamenId?: number;
}

export class Absence implements IAbsence {
    constructor(
        public id?: number,
        public motifAbsence?: string,
        public dateAbsence?: Moment,
        public inscriptionId?: number,
        public epreuveSpecialiteOptionId?: number,
        public etapeExamenId?: number
    ) {}
}
