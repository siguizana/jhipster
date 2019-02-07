/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { MembreJuryDetailComponent } from 'app/entities/membre-jury/membre-jury-detail.component';
import { MembreJury } from 'app/shared/model/membre-jury.model';

describe('Component Tests', () => {
    describe('MembreJury Management Detail Component', () => {
        let comp: MembreJuryDetailComponent;
        let fixture: ComponentFixture<MembreJuryDetailComponent>;
        const route = ({ data: of({ membreJury: new MembreJury(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [MembreJuryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MembreJuryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MembreJuryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.membreJury).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
