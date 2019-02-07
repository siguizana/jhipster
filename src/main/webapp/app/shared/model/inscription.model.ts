import { Moment } from 'moment';
import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';
import { IChoixEtablissement } from 'app/shared/model/choix-etablissement.model';
import { ICompositionCopie } from 'app/shared/model/composition-copie.model';
import { IAbsence } from 'app/shared/model/absence.model';
import { IResultat } from 'app/shared/model/resultat.model';
import { IDispense } from 'app/shared/model/dispense.model';
import { IFraude } from 'app/shared/model/fraude.model';

export interface IInscription {
    id?: number;
    numeroPV?: number;
    dateInscription?: Moment;
    aptitude?: boolean;
    residence?: string;
    echelon?: string;
    grade?: string;
    dateDernierePromotion?: Moment;
    diplome?: string;
    classeTenue?: string;
    nomPrenomPereOuTuteur?: string;
    nomPrenomMereOuTutrice?: string;
    adresseParentOuTiteur?: string;
    participeConcoursRattache?: boolean;
    epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[];
    choixEtablissements?: IChoixEtablissement[];
    compositionCopies?: ICompositionCopie[];
    absences?: IAbsence[];
    resultats?: IResultat[];
    dispenses?: IDispense[];
    fraudes?: IFraude[];
    villageSecteurId?: number;
    etablissementId?: number;
    sessionId?: number;
    optionConcoursRattacheId?: number;
    candidatId?: number;
    typeExamenId?: number;
    centreCompositioJuryId?: number;
    salleCompositionId?: number;
    juryId?: number;
}

export class Inscription implements IInscription {
    constructor(
        public id?: number,
        public numeroPV?: number,
        public dateInscription?: Moment,
        public aptitude?: boolean,
        public residence?: string,
        public echelon?: string,
        public grade?: string,
        public dateDernierePromotion?: Moment,
        public diplome?: string,
        public classeTenue?: string,
        public nomPrenomPereOuTuteur?: string,
        public nomPrenomMereOuTutrice?: string,
        public adresseParentOuTiteur?: string,
        public participeConcoursRattache?: boolean,
        public epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[],
        public choixEtablissements?: IChoixEtablissement[],
        public compositionCopies?: ICompositionCopie[],
        public absences?: IAbsence[],
        public resultats?: IResultat[],
        public dispenses?: IDispense[],
        public fraudes?: IFraude[],
        public villageSecteurId?: number,
        public etablissementId?: number,
        public sessionId?: number,
        public optionConcoursRattacheId?: number,
        public candidatId?: number,
        public typeExamenId?: number,
        public centreCompositioJuryId?: number,
        public salleCompositionId?: number,
        public juryId?: number
    ) {
        this.aptitude = this.aptitude || false;
        this.participeConcoursRattache = this.participeConcoursRattache || false;
    }
}
