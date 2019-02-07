import { IEnseigne } from 'app/shared/model/enseigne.model';
import { IInscription } from 'app/shared/model/inscription.model';
import { IChoixEtablissement } from 'app/shared/model/choix-etablissement.model';

export interface IEtablissement {
    id?: number;
    codeEtablissement?: string;
    libelleEtablissement?: string;
    nomResponsableEtablissement?: string;
    prenomResponsableEtablissement?: string;
    contactactEtablissement?: string;
    enseignes?: IEnseigne[];
    inscriptions?: IInscription[];
    choixEtablissements?: IChoixEtablissement[];
    cebId?: number;
    typeEtablissementId?: number;
    villageSecteurId?: number;
    centreCompositionId?: number;
}

export class Etablissement implements IEtablissement {
    constructor(
        public id?: number,
        public codeEtablissement?: string,
        public libelleEtablissement?: string,
        public nomResponsableEtablissement?: string,
        public prenomResponsableEtablissement?: string,
        public contactactEtablissement?: string,
        public enseignes?: IEnseigne[],
        public inscriptions?: IInscription[],
        public choixEtablissements?: IChoixEtablissement[],
        public cebId?: number,
        public typeEtablissementId?: number,
        public villageSecteurId?: number,
        public centreCompositionId?: number
    ) {}
}
