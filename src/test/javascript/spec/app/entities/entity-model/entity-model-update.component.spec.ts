import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityModelUpdateComponent } from 'app/entities/entity-model/entity-model-update.component';
import { EntityModelService } from 'app/entities/entity-model/entity-model.service';
import { EntityModel } from 'app/shared/model/entity-model.model';

describe('Component Tests', () => {
  describe('EntityModel Management Update Component', () => {
    let comp: EntityModelUpdateComponent;
    let fixture: ComponentFixture<EntityModelUpdateComponent>;
    let service: EntityModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityModelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntityModelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityModelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityModelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EntityModel('123');
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
        const entity = new EntityModel();
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
