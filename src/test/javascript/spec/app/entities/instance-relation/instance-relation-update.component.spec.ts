import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { InstanceRelationUpdateComponent } from 'app/entities/instance-relation/instance-relation-update.component';
import { InstanceRelationService } from 'app/entities/instance-relation/instance-relation.service';
import { InstanceRelation } from 'app/shared/model/instance-relation.model';

describe('Component Tests', () => {
  describe('InstanceRelation Management Update Component', () => {
    let comp: InstanceRelationUpdateComponent;
    let fixture: ComponentFixture<InstanceRelationUpdateComponent>;
    let service: InstanceRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [InstanceRelationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InstanceRelationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstanceRelationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstanceRelationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InstanceRelation('123');
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
        const entity = new InstanceRelation();
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
