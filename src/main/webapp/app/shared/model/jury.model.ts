import { ICentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';
import { IMembreJuryJury } from 'app/shared/model/membre-jury-jury.model';
import { IInscription } from 'app/shared/model/inscription.model';

export interface IJury {
    id?: number;
    libelleJury?: string;
    centreCompositioJuries?: ICentreCompositioJury[];
    membreJuryJuries?: IMembreJuryJury[];
    inscriptions?: IInscription[];
}

export class Jury implements IJury {
    constructor(
        public id?: number,
        public libelleJury?: string,
        public centreCompositioJuries?: ICentreCompositioJury[],
        public membreJuryJuries?: IMembreJuryJury[],
        public inscriptions?: IInscription[]
    ) {}
}
