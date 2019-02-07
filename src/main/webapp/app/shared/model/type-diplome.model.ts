import { IRetraitDiplome } from 'app/shared/model/retrait-diplome.model';

export interface ITypeDiplome {
    id?: number;
    libelleTypeDiplome?: string;
    retraitDiplomes?: IRetraitDiplome[];
}

export class TypeDiplome implements ITypeDiplome {
    constructor(public id?: number, public libelleTypeDiplome?: string, public retraitDiplomes?: IRetraitDiplome[]) {}
}
