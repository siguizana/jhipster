/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { EnseignantDetailComponent } from 'app/entities/enseignant/enseignant-detail.component';
import { Enseignant } from 'app/shared/model/enseignant.model';

describe('Component Tests', () => {
    describe('Enseignant Management Detail Component', () => {
        let comp: EnseignantDetailComponent;
        let fixture: ComponentFixture<EnseignantDetailComponent>;
        const route = ({ data: of({ enseignant: new Enseignant(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EnseignantDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EnseignantDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnseignantDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.enseignant).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
