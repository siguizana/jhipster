export interface INoteMembreCritere {
    id?: number;
    valeurNote?: number;
    membreJuryId?: number;
    critereChoixMembreJuryId?: number;
}

export class NoteMembreCritere implements INoteMembreCritere {
    constructor(public id?: number, public valeurNote?: number, public membreJuryId?: number, public critereChoixMembreJuryId?: number) {}
}
