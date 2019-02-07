import { IVillageSecteur } from 'app/shared/model/village-secteur.model';
import { ICeb } from 'app/shared/model/ceb.model';

export const enum TypeCommune {
    RURALE = 'RURALE',
    URBAINE = 'URBAINE'
}

export interface ICommune {
    id?: number;
    codeCommune?: string;
    libelleCommune?: string;
    typeCommune?: TypeCommune;
    villageSecteurs?: IVillageSecteur[];
    cebs?: ICeb[];
    provinceId?: number;
}

export class Commune implements ICommune {
    constructor(
        public id?: number,
        public codeCommune?: string,
        public libelleCommune?: string,
        public typeCommune?: TypeCommune,
        public villageSecteurs?: IVillageSecteur[],
        public cebs?: ICeb[],
        public provinceId?: number
    ) {}
}
