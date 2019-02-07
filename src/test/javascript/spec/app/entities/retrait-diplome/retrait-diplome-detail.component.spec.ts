/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { RetraitDiplomeDetailComponent } from 'app/entities/retrait-diplome/retrait-diplome-detail.component';
import { RetraitDiplome } from 'app/shared/model/retrait-diplome.model';

describe('Component Tests', () => {
    describe('RetraitDiplome Management Detail Component', () => {
        let comp: RetraitDiplomeDetailComponent;
        let fixture: ComponentFixture<RetraitDiplomeDetailComponent>;
        const route = ({ data: of({ retraitDiplome: new RetraitDiplome(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [RetraitDiplomeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RetraitDiplomeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RetraitDiplomeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.retraitDiplome).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
