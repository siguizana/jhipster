import { ICritereExamen } from 'app/shared/model/critere-examen.model';

export interface ITypeCritere {
    id?: number;
    libelle?: string;
    critereExamen?: ICritereExamen[];
}

export class TypeCritere implements ITypeCritere {
    constructor(public id?: number, public libelle?: string, public critereExamen?: ICritereExamen[]) {}
}
