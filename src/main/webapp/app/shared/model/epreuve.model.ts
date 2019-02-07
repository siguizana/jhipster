import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';

export interface IEpreuve {
    id?: number;
    libelle?: string;
    epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[];
    typeEpreuveId?: number;
}

export class Epreuve implements IEpreuve {
    constructor(
        public id?: number,
        public libelle?: string,
        public epreuveSpecialiteOptions?: IEpreuveSpecialiteOption[],
        public typeEpreuveId?: number
    ) {}
}
