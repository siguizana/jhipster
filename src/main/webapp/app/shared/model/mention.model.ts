import { IResultat } from 'app/shared/model/resultat.model';

export interface IMention {
    id?: number;
    libelleMention?: string;
    resultats?: IResultat[];
}

export class Mention implements IMention {
    constructor(public id?: number, public libelleMention?: string, public resultats?: IResultat[]) {}
}
