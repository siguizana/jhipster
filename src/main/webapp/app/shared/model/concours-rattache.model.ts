import { IOptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';

export interface IConcoursRattache {
    id?: number;
    libelleConcoursRattache?: string;
    optionConcoursRattaches?: IOptionConcoursRattache[];
    typeExamenId?: number;
}

export class ConcoursRattache implements IConcoursRattache {
    constructor(
        public id?: number,
        public libelleConcoursRattache?: string,
        public optionConcoursRattaches?: IOptionConcoursRattache[],
        public typeExamenId?: number
    ) {}
}
