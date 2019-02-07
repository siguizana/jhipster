import { ICommission } from 'app/shared/model/commission.model';

export interface IMembreJuryJury {
    id?: number;
    fonction?: string;
    status?: string;
    experience?: string;
    secteur?: string;
    quartier?: string;
    diplomeAcademique?: string;
    diplomeProfessionnel?: string;
    nomChefEtablissement?: string;
    prenomChefEtabissement?: string;
    avisChefEtablissement?: string;
    commissions?: ICommission[];
    juryId?: number;
    membreJuryId?: number;
}

export class MembreJuryJury implements IMembreJuryJury {
    constructor(
        public id?: number,
        public fonction?: string,
        public status?: string,
        public experience?: string,
        public secteur?: string,
        public quartier?: string,
        public diplomeAcademique?: string,
        public diplomeProfessionnel?: string,
        public nomChefEtablissement?: string,
        public prenomChefEtabissement?: string,
        public avisChefEtablissement?: string,
        public commissions?: ICommission[],
        public juryId?: number,
        public membreJuryId?: number
    ) {}
}
