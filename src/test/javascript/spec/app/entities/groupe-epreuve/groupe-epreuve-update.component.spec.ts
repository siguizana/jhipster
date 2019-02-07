/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { GroupeEpreuveUpdateComponent } from 'app/entities/groupe-epreuve/groupe-epreuve-update.component';
import { GroupeEpreuveService } from 'app/entities/groupe-epreuve/groupe-epreuve.service';
import { GroupeEpreuve } from 'app/shared/model/groupe-epreuve.model';

describe('Component Tests', () => {
    describe('GroupeEpreuve Management Update Component', () => {
        let comp: GroupeEpreuveUpdateComponent;
        let fixture: ComponentFixture<GroupeEpreuveUpdateComponent>;
        let service: GroupeEpreuveService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [GroupeEpreuveUpdateComponent]
            })
                .overrideTemplate(GroupeEpreuveUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GroupeEpreuveUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupeEpreuveService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new GroupeEpreuve(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.groupeEpreuve = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new GroupeEpreuve();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.groupeEpreuve = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
