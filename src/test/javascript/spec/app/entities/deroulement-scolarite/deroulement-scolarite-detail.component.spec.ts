/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { DeroulementScolariteDetailComponent } from 'app/entities/deroulement-scolarite/deroulement-scolarite-detail.component';
import { DeroulementScolarite } from 'app/shared/model/deroulement-scolarite.model';

describe('Component Tests', () => {
    describe('DeroulementScolarite Management Detail Component', () => {
        let comp: DeroulementScolariteDetailComponent;
        let fixture: ComponentFixture<DeroulementScolariteDetailComponent>;
        const route = ({ data: of({ deroulementScolarite: new DeroulementScolarite(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [DeroulementScolariteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DeroulementScolariteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeroulementScolariteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.deroulementScolarite).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
