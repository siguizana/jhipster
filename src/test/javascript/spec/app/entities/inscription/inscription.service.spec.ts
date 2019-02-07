/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InscriptionService } from 'app/entities/inscription/inscription.service';
import { IInscription, Inscription } from 'app/shared/model/inscription.model';

describe('Service Tests', () => {
    describe('Inscription Service', () => {
        let injector: TestBed;
        let service: InscriptionService;
        let httpMock: HttpTestingController;
        let elemDefault: IInscription;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(InscriptionService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Inscription(
                0,
                0,
                currentDate,
                false,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateInscription: currentDate.format(DATE_FORMAT),
                        dateDernierePromotion: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Inscription', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateInscription: currentDate.format(DATE_FORMAT),
                        dateDernierePromotion: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateInscription: currentDate,
                        dateDernierePromotion: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Inscription(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Inscription', async () => {
                const returnedFromService = Object.assign(
                    {
                        numeroPV: 1,
                        dateInscription: currentDate.format(DATE_FORMAT),
                        aptitude: true,
                        residence: 'BBBBBB',
                        echelon: 'BBBBBB',
                        grade: 'BBBBBB',
                        dateDernierePromotion: currentDate.format(DATE_FORMAT),
                        diplome: 'BBBBBB',
                        classeTenue: 'BBBBBB',
                        nomPrenomPereOuTuteur: 'BBBBBB',
                        nomPrenomMereOuTutrice: 'BBBBBB',
                        adresseParentOuTiteur: 'BBBBBB',
                        participeConcoursRattache: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateInscription: currentDate,
                        dateDernierePromotion: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Inscription', async () => {
                const returnedFromService = Object.assign(
                    {
                        numeroPV: 1,
                        dateInscription: currentDate.format(DATE_FORMAT),
                        aptitude: true,
                        residence: 'BBBBBB',
                        echelon: 'BBBBBB',
                        grade: 'BBBBBB',
                        dateDernierePromotion: currentDate.format(DATE_FORMAT),
                        diplome: 'BBBBBB',
                        classeTenue: 'BBBBBB',
                        nomPrenomPereOuTuteur: 'BBBBBB',
                        nomPrenomMereOuTutrice: 'BBBBBB',
                        adresseParentOuTiteur: 'BBBBBB',
                        participeConcoursRattache: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateInscription: currentDate,
                        dateDernierePromotion: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Inscription', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
