import { ICentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';
import { IEtablissement } from 'app/shared/model/etablissement.model';

export interface ICentreComposition {
    id?: number;
    libelleCentreComposition?: string;
    centreCompositioJuries?: ICentreCompositioJury[];
    etablissements?: IEtablissement[];
    cebId?: number;
    zoneExamenId?: number;
    typeCentreCompositionId?: number;
}

export class CentreComposition implements ICentreComposition {
    constructor(
        public id?: number,
        public libelleCentreComposition?: string,
        public centreCompositioJuries?: ICentreCompositioJury[],
        public etablissements?: IEtablissement[],
        public cebId?: number,
        public zoneExamenId?: number,
        public typeCentreCompositionId?: number
    ) {}
}
