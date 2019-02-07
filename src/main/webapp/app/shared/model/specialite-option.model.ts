import { IChoixEtablissement } from 'app/shared/model/choix-etablissement.model';
import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';

export interface ISpecialiteOption {
    id?: number;
    libelle?: string;
    choixEtablissements?: IChoixEtablissement[];
    epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[];
    typeExamenId?: number;
    filiereId?: number;
}

export class SpecialiteOption implements ISpecialiteOption {
    constructor(
        public id?: number,
        public libelle?: string,
        public choixEtablissements?: IChoixEtablissement[],
        public epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[],
        public typeExamenId?: number,
        public filiereId?: number
    ) {}
}
