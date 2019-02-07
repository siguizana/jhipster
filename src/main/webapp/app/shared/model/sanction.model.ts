export interface ISanction {
    id?: number;
    libelleSanction?: string;
    fraudeId?: number;
}

export class Sanction implements ISanction {
    constructor(public id?: number, public libelleSanction?: string, public fraudeId?: number) {}
}
