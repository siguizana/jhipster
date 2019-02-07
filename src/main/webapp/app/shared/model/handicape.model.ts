import { IDispense } from 'app/shared/model/dispense.model';

export interface IHandicape {
    id?: number;
    libelle?: string;
    dispenses?: IDispense[];
}

export class Handicape implements IHandicape {
    constructor(public id?: number, public libelle?: string, public dispenses?: IDispense[]) {}
}
