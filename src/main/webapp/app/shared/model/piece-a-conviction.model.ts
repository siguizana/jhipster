export interface IPieceAConviction {
    id?: number;
    libellePieceAconviction?: string;
    fraudeId?: number;
}

export class PieceAConviction implements IPieceAConviction {
    constructor(public id?: number, public libellePieceAconviction?: string, public fraudeId?: number) {}
}
