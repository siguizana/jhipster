import { Moment } from 'moment';
import { IInscription } from 'app/shared/model/inscription.model';
import { ICritereExamen } from 'app/shared/model/critere-examen.model';
import { IEnseigne } from 'app/shared/model/enseigne.model';

export interface ISession {
    id?: number;
    libelle?: string;
    dateOuverture?: Moment;
    dateFermeture?: Moment;
    anneeSession?: number;
    inscriptions?: IInscription[];
    critereExamen?: ICritereExamen[];
    enseignes?: IEnseigne[];
}

export class Session implements ISession {
    constructor(
        public id?: number,
        public libelle?: string,
        public dateOuverture?: Moment,
        public dateFermeture?: Moment,
        public anneeSession?: number,
        public inscriptions?: IInscription[],
        public critereExamen?: ICritereExamen[],
        public enseignes?: IEnseigne[]
    ) {}
}
