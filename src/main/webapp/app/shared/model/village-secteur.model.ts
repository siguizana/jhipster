import { IEtablissement } from 'app/shared/model/etablissement.model';
import { IInscription } from 'app/shared/model/inscription.model';

export interface IVillageSecteur {
    id?: number;
    codeVillageSecteur?: string;
    libelleVillageSecteur?: string;
    etablissements?: IEtablissement[];
    inscriptions?: IInscription[];
    communeId?: number;
}

export class VillageSecteur implements IVillageSecteur {
    constructor(
        public id?: number,
        public codeVillageSecteur?: string,
        public libelleVillageSecteur?: string,
        public etablissements?: IEtablissement[],
        public inscriptions?: IInscription[],
        public communeId?: number
    ) {}
}
