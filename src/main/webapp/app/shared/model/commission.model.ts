import { IMembreJuryJury } from 'app/shared/model/membre-jury-jury.model';

export interface ICommission {
    id?: number;
    libelleCommission?: string;
    membreJuryJuries?: IMembreJuryJury[];
}

export class Commission implements ICommission {
    constructor(public id?: number, public libelleCommission?: string, public membreJuryJuries?: IMembreJuryJury[]) {}
}
