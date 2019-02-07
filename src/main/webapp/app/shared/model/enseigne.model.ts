export interface IEnseigne {
    id?: number;
    classeEnseigne?: string;
    etablissementId?: number;
    enseignantId?: number;
    sessionId?: number;
}

export class Enseigne implements IEnseigne {
    constructor(
        public id?: number,
        public classeEnseigne?: string,
        public etablissementId?: number,
        public enseignantId?: number,
        public sessionId?: number
    ) {}
}
