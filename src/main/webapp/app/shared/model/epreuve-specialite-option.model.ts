import { ICompositionCopie } from 'app/shared/model/composition-copie.model';
import { IAbsence } from 'app/shared/model/absence.model';
import { IDispense } from 'app/shared/model/dispense.model';

export interface IEpreuveSpecialiteOption {
    id?: number;
    epreuveRachat?: boolean;
    epreuveAdmissibilite?: boolean;
    epreuveFacultative?: boolean;
    noteEliminatoire?: number;
    coefficient?: number;
    compositionCopies?: ICompositionCopie[];
    absences?: IAbsence[];
    inscriptionId?: number;
    epreuveId?: number;
    specialiteOptionId?: number;
    groupeEpreuveId?: number;
    dispenses?: IDispense[];
}

export class EpreuveSpecialiteOption implements IEpreuveSpecialiteOption {
    constructor(
        public id?: number,
        public epreuveRachat?: boolean,
        public epreuveAdmissibilite?: boolean,
        public epreuveFacultative?: boolean,
        public noteEliminatoire?: number,
        public coefficient?: number,
        public compositionCopies?: ICompositionCopie[],
        public absences?: IAbsence[],
        public inscriptionId?: number,
        public epreuveId?: number,
        public specialiteOptionId?: number,
        public groupeEpreuveId?: number,
        public dispenses?: IDispense[]
    ) {
        this.epreuveRachat = this.epreuveRachat || false;
        this.epreuveAdmissibilite = this.epreuveAdmissibilite || false;
        this.epreuveFacultative = this.epreuveFacultative || false;
    }
}
