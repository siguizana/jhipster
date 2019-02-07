export interface IDeroulementScolarite {
    id?: number;
    anneScolarite?: string;
    classe?: string;
    candidatId?: number;
}

export class DeroulementScolarite implements IDeroulementScolarite {
    constructor(public id?: number, public anneScolarite?: string, public classe?: string, public candidatId?: number) {}
}
