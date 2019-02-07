import { IEtablissement } from 'app/shared/model/etablissement.model';
import { ICentreComposition } from 'app/shared/model/centre-composition.model';

export const enum TypeCeb {
    CEBREFORME = 'CEBREFORME',
    CEBNONREFORME = 'CEBNONREFORME'
}

export interface ICeb {
    id?: number;
    codeCeb?: string;
    libelleCeb?: string;
    typeCeb?: TypeCeb;
    etablissements?: IEtablissement[];
    centreCompositions?: ICentreComposition[];
    communeId?: number;
}

export class Ceb implements ICeb {
    constructor(
        public id?: number,
        public codeCeb?: string,
        public libelleCeb?: string,
        public typeCeb?: TypeCeb,
        public etablissements?: IEtablissement[],
        public centreCompositions?: ICentreComposition[],
        public communeId?: number
    ) {}
}
