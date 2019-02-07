import { ICentreComposition } from 'app/shared/model/centre-composition.model';

export interface ITypeCentreComposition {
    id?: number;
    libelleTypeCentreComposition?: string;
    centreCompositions?: ICentreComposition[];
}

export class TypeCentreComposition implements ITypeCentreComposition {
    constructor(public id?: number, public libelleTypeCentreComposition?: string, public centreCompositions?: ICentreComposition[]) {}
}
