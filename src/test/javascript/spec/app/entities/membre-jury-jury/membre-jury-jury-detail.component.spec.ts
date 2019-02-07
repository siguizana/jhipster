/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { MembreJuryJuryDetailComponent } from 'app/entities/membre-jury-jury/membre-jury-jury-detail.component';
import { MembreJuryJury } from 'app/shared/model/membre-jury-jury.model';

describe('Component Tests', () => {
    describe('MembreJuryJury Management Detail Component', () => {
        let comp: MembreJuryJuryDetailComponent;
        let fixture: ComponentFixture<MembreJuryJuryDetailComponent>;
        const route = ({ data: of({ membreJuryJury: new MembreJuryJury(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [MembreJuryJuryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MembreJuryJuryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MembreJuryJuryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.membreJuryJury).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
