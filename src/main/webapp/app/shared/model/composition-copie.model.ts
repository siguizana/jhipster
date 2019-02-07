import { Moment } from 'moment';
import { IReclamation } from 'app/shared/model/reclamation.model';
import { IMembreJury } from 'app/shared/model/membre-jury.model';

export interface ICompositionCopie {
    id?: number;
    note?: number;
    dateComposition?: Moment;
    numeroAnonymat?: string;
    reclamations?: IReclamation[];
    inscriptionId?: number;
    epreuveSpecialiteOptionId?: number;
    etapeExamenId?: number;
    membreJuries?: IMembreJury[];
}

export class CompositionCopie implements ICompositionCopie {
    constructor(
        public id?: number,
        public note?: number,
        public dateComposition?: Moment,
        public numeroAnonymat?: string,
        public reclamations?: IReclamation[],
        public inscriptionId?: number,
        public epreuveSpecialiteOptionId?: number,
        public etapeExamenId?: number,
        public membreJuries?: IMembreJury[]
    ) {}
}
