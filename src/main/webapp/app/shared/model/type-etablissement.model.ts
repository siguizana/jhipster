import { IEtablissement } from 'app/shared/model/etablissement.model';

export interface ITypeEtablissement {
    id?: number;
    libelleTypeEtablissement?: string;
    etablissements?: IEtablissement[];
}

export class TypeEtablissement implements ITypeEtablissement {
    constructor(public id?: number, public libelleTypeEtablissement?: string, public etablissements?: IEtablissement[]) {}
}
