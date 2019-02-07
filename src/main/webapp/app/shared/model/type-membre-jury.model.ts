import { IMembreJury } from 'app/shared/model/membre-jury.model';
import { ICritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';

export interface ITypeMembreJury {
    id?: number;
    libelle?: string;
    membreJuries?: IMembreJury[];
    critereChoixMembreJuries?: ICritereChoixMembreJury[];
}

export class TypeMembreJury implements ITypeMembreJury {
    constructor(
        public id?: number,
        public libelle?: string,
        public membreJuries?: IMembreJury[],
        public critereChoixMembreJuries?: ICritereChoixMembreJury[]
    ) {}
}
