/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { NoteMembreCritereUpdateComponent } from 'app/entities/note-membre-critere/note-membre-critere-update.component';
import { NoteMembreCritereService } from 'app/entities/note-membre-critere/note-membre-critere.service';
import { NoteMembreCritere } from 'app/shared/model/note-membre-critere.model';

describe('Component Tests', () => {
    describe('NoteMembreCritere Management Update Component', () => {
        let comp: NoteMembreCritereUpdateComponent;
        let fixture: ComponentFixture<NoteMembreCritereUpdateComponent>;
        let service: NoteMembreCritereService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [NoteMembreCritereUpdateComponent]
            })
                .overrideTemplate(NoteMembreCritereUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NoteMembreCritereUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NoteMembreCritereService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new NoteMembreCritere(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.noteMembreCritere = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new NoteMembreCritere();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.noteMembreCritere = entity;
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
