import { ISalleComposition } from 'app/shared/model/salle-composition.model';
import { IInscription } from 'app/shared/model/inscription.model';

export interface ICentreCompositioJury {
    id?: number;
    salleCompositions?: ISalleComposition[];
    inscriptions?: IInscription[];
    centreCompositionId?: number;
    juryId?: number;
}

export class CentreCompositioJury implements ICentreCompositioJury {
    constructor(
        public id?: number,
        public salleCompositions?: ISalleComposition[],
        public inscriptions?: IInscription[],
        public centreCompositionId?: number,
        public juryId?: number
    ) {}
}
