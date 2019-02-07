/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SurveilleService } from 'app/entities/surveille/surveille.service';
import { ISurveille, Surveille } from 'app/shared/model/surveille.model';

describe('Service Tests', () => {
    describe('Surveille Service', () => {
        let injector: TestBed;
        let service: SurveilleService;
        let httpMock: HttpTestingController;
        let elemDefault: ISurveille;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(SurveilleService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Surveille(0, currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebutSurveillance: currentDate.format(DATE_TIME_FORMAT),
                        dateFinSurveillance: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Surveille', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateDebutSurveillance: currentDate.format(DATE_TIME_FORMAT),
                        dateFinSurveillance: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateDebutSurveillance: currentDate,
                        dateFinSurveillance: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Surveille(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Surveille', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebutSurveillance: currentDate.format(DATE_TIME_FORMAT),
                        dateFinSurveillance: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateDebutSurveillance: currentDate,
                        dateFinSurveillance: currentDate
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

            it('should return a list of Surveille', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebutSurveillance: currentDate.format(DATE_TIME_FORMAT),
                        dateFinSurveillance: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateDebutSurveillance: currentDate,
                        dateFinSurveillance: currentDate
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

            it('should delete a Surveille', async () => {
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
