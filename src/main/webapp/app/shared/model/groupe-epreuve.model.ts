import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';

export interface IGroupeEpreuve {
    id?: number;
    libelle?: string;
    epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[];
}

export class GroupeEpreuve implements IGroupeEpreuve {
    constructor(public id?: number, public libelle?: string, public epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[]) {}
}
