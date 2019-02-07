import { ISanction } from 'app/shared/model/sanction.model';
import { IPieceAConviction } from 'app/shared/model/piece-a-conviction.model';
import { IMembreJury } from 'app/shared/model/membre-jury.model';

export interface IFraude {
    id?: number;
    libelleFraude?: string;
    sanctions?: ISanction[];
    pieceAConvictions?: IPieceAConviction[];
    typeFraudeId?: number;
    inscriptionId?: number;
    membreJuries?: IMembreJury[];
}

export class Fraude implements IFraude {
    constructor(
        public id?: number,
        public libelleFraude?: string,
        public sanctions?: ISanction[],
        public pieceAConvictions?: IPieceAConviction[],
        public typeFraudeId?: number,
        public inscriptionId?: number,
        public membreJuries?: IMembreJury[]
    ) {}
}
