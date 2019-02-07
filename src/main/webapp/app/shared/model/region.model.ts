import { IProvince } from 'app/shared/model/province.model';
import { ICritereExamen } from 'app/shared/model/critere-examen.model';
import { ICentreExamen } from 'app/shared/model/centre-examen.model';

export interface IRegion {
    id?: number;
    codeRegion?: string;
    libelleRegion?: string;
    provinces?: IProvince[];
    critereExamen?: ICritereExamen[];
    centreExamen?: ICentreExamen[];
}

export class Region implements IRegion {
    constructor(
        public id?: number,
        public codeRegion?: string,
        public libelleRegion?: string,
        public provinces?: IProvince[],
        public critereExamen?: ICritereExamen[],
        public centreExamen?: ICentreExamen[]
    ) {}
}
