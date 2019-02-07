import { IResultat } from 'app/shared/model/resultat.model';
import { ICritereExamen } from 'app/shared/model/critere-examen.model';
import { IInscription } from 'app/shared/model/inscription.model';

export interface IOptionConcoursRattache {
    id?: number;
    libelleOptionConcoursRattache?: string;
    resultats?: IResultat[];
    critereExamen?: ICritereExamen[];
    inscriptions?: IInscription[];
    concoursRattacheId?: number;
}

export class OptionConcoursRattache implements IOptionConcoursRattache {
    constructor(
        public id?: number,
        public libelleOptionConcoursRattache?: string,
        public resultats?: IResultat[],
        public critereExamen?: ICritereExamen[],
        public inscriptions?: IInscription[],
        public concoursRattacheId?: number
    ) {}
}
