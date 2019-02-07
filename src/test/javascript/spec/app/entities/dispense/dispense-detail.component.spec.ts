/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { DispenseDetailComponent } from 'app/entities/dispense/dispense-detail.component';
import { Dispense } from 'app/shared/model/dispense.model';

describe('Component Tests', () => {
    describe('Dispense Management Detail Component', () => {
        let comp: DispenseDetailComponent;
        let fixture: ComponentFixture<DispenseDetailComponent>;
        const route = ({ data: of({ dispense: new Dispense(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [DispenseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DispenseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DispenseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dispense).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
