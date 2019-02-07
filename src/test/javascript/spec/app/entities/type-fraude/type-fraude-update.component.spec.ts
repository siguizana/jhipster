/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeFraudeUpdateComponent } from 'app/entities/type-fraude/type-fraude-update.component';
import { TypeFraudeService } from 'app/entities/type-fraude/type-fraude.service';
import { TypeFraude } from 'app/shared/model/type-fraude.model';

describe('Component Tests', () => {
    describe('TypeFraude Management Update Component', () => {
        let comp: TypeFraudeUpdateComponent;
        let fixture: ComponentFixture<TypeFraudeUpdateComponent>;
        let service: TypeFraudeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeFraudeUpdateComponent]
            })
                .overrideTemplate(TypeFraudeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeFraudeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeFraudeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeFraude(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeFraude = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeFraude();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeFraude = entity;
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
