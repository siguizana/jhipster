import { IFraude } from 'app/shared/model/fraude.model';

export interface ITypeFraude {
    id?: number;
    libelleTypeFraude?: string;
    fraudes?: IFraude[];
}

export class TypeFraude implements ITypeFraude {
    constructor(public id?: number, public libelleTypeFraude?: string, public fraudes?: IFraude[]) {}
}
