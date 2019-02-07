import { Moment } from 'moment';

export interface IRetraitDiplome {
    id?: number;
    dateRetrait?: Moment;
    resultatId?: number;
    typeDiplomeId?: number;
}

export class RetraitDiplome implements IRetraitDiplome {
    constructor(public id?: number, public dateRetrait?: Moment, public resultatId?: number, public typeDiplomeId?: number) {}
}
