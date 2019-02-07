import { ICommune } from 'app/shared/model/commune.model';

export interface IProvince {
    id?: number;
    codeProvince?: string;
    libelleProvince?: string;
    communes?: ICommune[];
    regionId?: number;
}

export class Province implements IProvince {
    constructor(
        public id?: number,
        public codeProvince?: string,
        public libelleProvince?: string,
        public communes?: ICommune[],
        public regionId?: number
    ) {}
}
