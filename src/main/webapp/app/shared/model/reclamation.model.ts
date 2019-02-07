import { Moment } from 'moment';

export interface IReclamation {
    id?: number;
    motifReclamation?: string;
    dateReclamation?: Moment;
    noteReclamation?: number;
    statutReclamation?: boolean;
    compositionCopieId?: number;
}

export class Reclamation implements IReclamation {
    constructor(
        public id?: number,
        public motifReclamation?: string,
        public dateReclamation?: Moment,
        public noteReclamation?: number,
        public statutReclamation?: boolean,
        public compositionCopieId?: number
    ) {
        this.statutReclamation = this.statutReclamation || false;
    }
}
