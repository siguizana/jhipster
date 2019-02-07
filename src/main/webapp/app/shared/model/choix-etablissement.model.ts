export interface IChoixEtablissement {
    id?: number;
    priorite?: string;
    inscriptionId?: number;
    etablissementId?: number;
    specialiteOptionId?: number;
}

export class ChoixEtablissement implements IChoixEtablissement {
    constructor(
        public id?: number,
        public priorite?: string,
        public inscriptionId?: number,
        public etablissementId?: number,
        public specialiteOptionId?: number
    ) {}
}
