import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityInstanceUpdateComponent } from 'app/entities/entity-instance/entity-instance-update.component';
import { EntityInstanceService } from 'app/entities/entity-instance/entity-instance.service';
import { EntityInstance } from 'app/shared/model/entity-instance.model';

describe('Component Tests', () => {
  describe('EntityInstance Management Update Component', () => {
    let comp: EntityInstanceUpdateComponent;
    let fixture: ComponentFixture<EntityInstanceUpdateComponent>;
    let service: EntityInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityInstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntityInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EntityInstance('123');
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
        const entity = new EntityInstance();
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
