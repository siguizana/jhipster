import { IZoneExamen } from 'app/shared/model/zone-examen.model';

export interface ICentreExamen {
    id?: number;
    libelleCentreExamen?: string;
    zoneExamen?: IZoneExamen[];
    regionId?: number;
}

export class CentreExamen implements ICentreExamen {
    constructor(public id?: number, public libelleCentreExamen?: string, public zoneExamen?: IZoneExamen[], public regionId?: number) {}
}
