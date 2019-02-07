/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CandidatService } from 'app/entities/candidat/candidat.service';
import { ICandidat, Candidat, Sexe } from 'app/shared/model/candidat.model';

describe('Service Tests', () => {
    describe('Candidat Service', () => {
        let injector: TestBed;
        let service: CandidatService;
        let httpMock: HttpTestingController;
        let elemDefault: ICandidat;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CandidatService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Candidat(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                Sexe.MASCULIN,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateNaissance: currentDate.format(DATE_FORMAT),
                        dateEnregistrementCandidat: currentDate.format(DATE_FORMAT),
                        dateDebutCariereCandidat: currentDate.format(DATE_FORMAT)
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

            it('should create a Candidat', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateNaissance: currentDate.format(DATE_FORMAT),
                        dateEnregistrementCandidat: currentDate.format(DATE_FORMAT),
                        dateDebutCariereCandidat: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateNaissance: currentDate,
                        dateEnregistrementCandidat: currentDate,
                        dateDebutCariereCandidat: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Candidat(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Candidat', async () => {
                const returnedFromService = Object.assign(
                    {
                        codeCandidat: 'BBBBBB',
                        nomCandidat: 'BBBBBB',
                        prenomCandidat: 'BBBBBB',
                        dateNaissance: currentDate.format(DATE_FORMAT),
                        lieuNaissance: 'BBBBBB',
                        paysNaissance: 'BBBBBB',
                        sexe: 'BBBBBB',
                        telephoneCandidat: 'BBBBBB',
                        nationaliteCandidat: 'BBBBBB',
                        matriculeCandidat: 'BBBBBB',
                        dateEnregistrementCandidat: currentDate.format(DATE_FORMAT),
                        dateDebutCariereCandidat: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateNaissance: currentDate,
                        dateEnregistrementCandidat: currentDate,
                        dateDebutCariereCandidat: currentDate
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

            it('should return a list of Candidat', async () => {
                const returnedFromService = Object.assign(
                    {
                        codeCandidat: 'BBBBBB',
                        nomCandidat: 'BBBBBB',
                        prenomCandidat: 'BBBBBB',
                        dateNaissance: currentDate.format(DATE_FORMAT),
                        lieuNaissance: 'BBBBBB',
                        paysNaissance: 'BBBBBB',
                        sexe: 'BBBBBB',
                        telephoneCandidat: 'BBBBBB',
                        nationaliteCandidat: 'BBBBBB',
                        matriculeCandidat: 'BBBBBB',
                        dateEnregistrementCandidat: currentDate.format(DATE_FORMAT),
                        dateDebutCariereCandidat: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateNaissance: currentDate,
                        dateEnregistrementCandidat: currentDate,
                        dateDebutCariereCandidat: currentDate
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

            it('should delete a Candidat', async () => {
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
