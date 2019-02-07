import { IResultat } from 'app/shared/model/resultat.model';

export interface ITypeDecision {
    id?: number;
    libelleTypeDecision?: string;
    resultats?: IResultat[];
}

export class TypeDecision implements ITypeDecision {
    constructor(public id?: number, public libelleTypeDecision?: string, public resultats?: IResultat[]) {}
}
