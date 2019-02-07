import { INoteMembreCritere } from 'app/shared/model/note-membre-critere.model';
import { ITypeMembreJury } from 'app/shared/model/type-membre-jury.model';

export interface ICritereChoixMembreJury {
    id?: number;
    libelle?: string;
    noteParDefaut?: number;
    noteMembreCriteres?: INoteMembreCritere[];
    typeMembreJuries?: ITypeMembreJury[];
}

export class CritereChoixMembreJury implements ICritereChoixMembreJury {
    constructor(
        public id?: number,
        public libelle?: string,
        public noteParDefaut?: number,
        public noteMembreCriteres?: INoteMembreCritere[],
        public typeMembreJuries?: ITypeMembreJury[]
    ) {}
}
