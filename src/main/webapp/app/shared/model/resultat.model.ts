import { IRetraitDiplome } from 'app/shared/model/retrait-diplome.model';

export interface IResultat {
    id?: number;
    notePondere?: number;
    retraitDiplomes?: IRetraitDiplome[];
    etapeExamenId?: number;
    mentionId?: number;
    typeDecisionId?: number;
    optionConcoursRattacheId?: number;
    inscriptionId?: number;
}

export class Resultat implements IResultat {
    constructor(
        public id?: number,
        public notePondere?: number,
        public retraitDiplomes?: IRetraitDiplome[],
        public etapeExamenId?: number,
        public mentionId?: number,
        public typeDecisionId?: number,
        public optionConcoursRattacheId?: number,
        public inscriptionId?: number
    ) {}
}
