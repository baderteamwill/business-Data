import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityinstanceUpdateComponent } from 'app/entities/entityinstance/entityinstance-update.component';
import { EntityinstanceService } from 'app/entities/entityinstance/entityinstance.service';
import { Entityinstance } from 'app/shared/model/entityinstance.model';

describe('Component Tests', () => {
  describe('Entityinstance Management Update Component', () => {
    let comp: EntityinstanceUpdateComponent;
    let fixture: ComponentFixture<EntityinstanceUpdateComponent>;
    let service: EntityinstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityinstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntityinstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityinstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityinstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Entityinstance('123');
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
        const entity = new Entityinstance();
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
