import { IEpreuve } from 'app/shared/model/epreuve.model';

export interface ITypeEpreuve {
    id?: number;
    libelle?: string;
    epreuves?: IEpreuve[];
}

export class TypeEpreuve implements ITypeEpreuve {
    constructor(public id?: number, public libelle?: string, public epreuves?: IEpreuve[]) {}
}
