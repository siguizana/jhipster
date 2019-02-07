/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { MentionUpdateComponent } from 'app/entities/mention/mention-update.component';
import { MentionService } from 'app/entities/mention/mention.service';
import { Mention } from 'app/shared/model/mention.model';

describe('Component Tests', () => {
    describe('Mention Management Update Component', () => {
        let comp: MentionUpdateComponent;
        let fixture: ComponentFixture<MentionUpdateComponent>;
        let service: MentionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [MentionUpdateComponent]
            })
                .overrideTemplate(MentionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MentionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MentionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Mention(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mention = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Mention();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mention = entity;
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
