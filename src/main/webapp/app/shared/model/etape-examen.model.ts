import { ICompositionCopie } from 'app/shared/model/composition-copie.model';
import { IAbsence } from 'app/shared/model/absence.model';
import { IResultat } from 'app/shared/model/resultat.model';

export interface IEtapeExamen {
    id?: number;
    libelleEtapeExamen?: string;
    compositionCopies?: ICompositionCopie[];
    absences?: IAbsence[];
    resultats?: IResultat[];
    typeExamenId?: number;
}

export class EtapeExamen implements IEtapeExamen {
    constructor(
        public id?: number,
        public libelleEtapeExamen?: string,
        public compositionCopies?: ICompositionCopie[],
        public absences?: IAbsence[],
        public resultats?: IResultat[],
        public typeExamenId?: number
    ) {}
}
