import { ISurveille } from 'app/shared/model/surveille.model';
import { INoteMembreCritere } from 'app/shared/model/note-membre-critere.model';
import { IMembreJuryJury } from 'app/shared/model/membre-jury-jury.model';
import { IFraude } from 'app/shared/model/fraude.model';
import { ICompositionCopie } from 'app/shared/model/composition-copie.model';

export interface IMembreJury {
    id?: number;
    nomMembre?: string;
    prenomMembre?: string;
    numeroCNIB?: string;
    matricule?: string;
    surveilles?: ISurveille[];
    noteMembreCriteres?: INoteMembreCritere[];
    membreJuryJuries?: IMembreJuryJury[];
    fraudes?: IFraude[];
    compositionCopies?: ICompositionCopie[];
    typeMembreJuryId?: number;
}

export class MembreJury implements IMembreJury {
    constructor(
        public id?: number,
        public nomMembre?: string,
        public prenomMembre?: string,
        public numeroCNIB?: string,
        public matricule?: string,
        public surveilles?: ISurveille[],
        public noteMembreCriteres?: INoteMembreCritere[],
        public membreJuryJuries?: IMembreJuryJury[],
        public fraudes?: IFraude[],
        public compositionCopies?: ICompositionCopie[],
        public typeMembreJuryId?: number
    ) {}
}
