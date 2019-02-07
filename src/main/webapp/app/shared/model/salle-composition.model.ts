import { ISurveille } from 'app/shared/model/surveille.model';
import { IInscription } from 'app/shared/model/inscription.model';

export interface ISalleComposition {
    id?: number;
    libelle?: string;
    capaciteSalle?: string;
    surveilles?: ISurveille[];
    inscriptions?: IInscription[];
    centreCompositioJuryId?: number;
}

export class SalleComposition implements ISalleComposition {
    constructor(
        public id?: number,
        public libelle?: string,
        public capaciteSalle?: string,
        public surveilles?: ISurveille[],
        public inscriptions?: IInscription[],
        public centreCompositioJuryId?: number
    ) {}
}
