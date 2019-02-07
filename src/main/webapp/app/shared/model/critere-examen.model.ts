export interface ICritereExamen {
    id?: number;
    libelleCritereExamen?: string;
    valeur?: number;
    regionId?: number;
    optionConcoursRattacheId?: number;
    sessionId?: number;
    typeExamenId?: number;
    typeCritereId?: number;
}

export class CritereExamen implements ICritereExamen {
    constructor(
        public id?: number,
        public libelleCritereExamen?: string,
        public valeur?: number,
        public regionId?: number,
        public optionConcoursRattacheId?: number,
        public sessionId?: number,
        public typeExamenId?: number,
        public typeCritereId?: number
    ) {}
}
