import { Moment } from 'moment';
import { IInscription } from 'app/shared/model/inscription.model';
import { IDeroulementScolarite } from 'app/shared/model/deroulement-scolarite.model';

export const enum Sexe {
    MASCULIN = 'MASCULIN',
    FEMININ = 'FEMININ'
}

export interface ICandidat {
    id?: number;
    codeCandidat?: string;
    nomCandidat?: string;
    prenomCandidat?: string;
    dateNaissance?: Moment;
    lieuNaissance?: string;
    paysNaissance?: string;
    sexe?: Sexe;
    telephoneCandidat?: string;
    nationaliteCandidat?: string;
    matriculeCandidat?: string;
    dateEnregistrementCandidat?: Moment;
    dateDebutCariereCandidat?: Moment;
    inscriptions?: IInscription[];
    deroulementScolarites?: IDeroulementScolarite[];
}

export class Candidat implements ICandidat {
    constructor(
        public id?: number,
        public codeCandidat?: string,
        public nomCandidat?: string,
        public prenomCandidat?: string,
        public dateNaissance?: Moment,
        public lieuNaissance?: string,
        public paysNaissance?: string,
        public sexe?: Sexe,
        public telephoneCandidat?: string,
        public nationaliteCandidat?: string,
        public matriculeCandidat?: string,
        public dateEnregistrementCandidat?: Moment,
        public dateDebutCariereCandidat?: Moment,
        public inscriptions?: IInscription[],
        public deroulementScolarites?: IDeroulementScolarite[]
    ) {}
}
