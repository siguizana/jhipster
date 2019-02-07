import { ISpecialiteOption } from 'app/shared/model/specialite-option.model';

export interface IFiliere {
    id?: number;
    libelle?: string;
    specialiteOptions?: ISpecialiteOption[];
}

export class Filiere implements IFiliere {
    constructor(public id?: number, public libelle?: string, public specialiteOptions?: ISpecialiteOption[]) {}
}
