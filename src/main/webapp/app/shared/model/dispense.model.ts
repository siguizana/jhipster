import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';

export interface IDispense {
    id?: number;
    motitDispense?: string;
    nomPrenomMedecin?: string;
    epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[];
    inscriptionId?: number;
    handicapeId?: number;
}

export class Dispense implements IDispense {
    constructor(
        public id?: number,
        public motitDispense?: string,
        public nomPrenomMedecin?: string,
        public epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[],
        public inscriptionId?: number,
        public handicapeId?: number
    ) {}
}
