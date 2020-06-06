import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityRelationUpdateComponent } from 'app/entities/entity-relation/entity-relation-update.component';
import { EntityRelationService } from 'app/entities/entity-relation/entity-relation.service';
import { EntityRelation } from 'app/shared/model/entity-relation.model';

describe('Component Tests', () => {
  describe('EntityRelation Management Update Component', () => {
    let comp: EntityRelationUpdateComponent;
    let fixture: ComponentFixture<EntityRelationUpdateComponent>;
    let service: EntityRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityRelationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntityRelationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityRelationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityRelationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EntityRelation('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EntityRelation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
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
