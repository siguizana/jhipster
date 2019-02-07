import { IEtapeExamen } from 'app/shared/model/etape-examen.model';
import { IConcoursRattache } from 'app/shared/model/concours-rattache.model';
import { ICritereExamen } from 'app/shared/model/critere-examen.model';
import { ISpecialiteOption } from 'app/shared/model/specialite-option.model';
import { IInscription } from 'app/shared/model/inscription.model';

export interface ITypeExamen {
    id?: number;
    libelle?: string;
    etapeExamen?: IEtapeExamen[];
    concoursRattaches?: IConcoursRattache[];
    critereExamen?: ICritereExamen[];
    specialiteOptions?: ISpecialiteOption[];
    inscriptions?: IInscription[];
}

export class TypeExamen implements ITypeExamen {
    constructor(
        public id?: number,
        public libelle?: string,
        public etapeExamen?: IEtapeExamen[],
        public concoursRattaches?: IConcoursRattache[],
        public critereExamen?: ICritereExamen[],
        public specialiteOptions?: ISpecialiteOption[],
        public inscriptions?: IInscription[]
    ) {}
}
