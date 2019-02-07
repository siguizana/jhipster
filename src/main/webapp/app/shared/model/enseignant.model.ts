import { IEnseigne } from 'app/shared/model/enseigne.model';

export interface IEnseignant {
    id?: number;
    matricule?: string;
    nomEnseignant?: string;
    prenomEnseignant?: string;
    contactEnseignant?: string;
    gradeEnseignant?: string;
    echelonEnseignant?: string;
    enseignes?: IEnseigne[];
}

export class Enseignant implements IEnseignant {
    constructor(
        public id?: number,
        public matricule?: string,
        public nomEnseignant?: string,
        public prenomEnseignant?: string,
        public contactEnseignant?: string,
        public gradeEnseignant?: string,
        public echelonEnseignant?: string,
        public enseignes?: IEnseigne[]
    ) {}
}
