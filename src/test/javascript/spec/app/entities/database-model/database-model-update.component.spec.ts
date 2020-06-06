import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { DatabaseModelUpdateComponent } from 'app/entities/database-model/database-model-update.component';
import { DatabaseModelService } from 'app/entities/database-model/database-model.service';
import { DatabaseModel } from 'app/shared/model/database-model.model';

describe('Component Tests', () => {
  describe('DatabaseModel Management Update Component', () => {
    let comp: DatabaseModelUpdateComponent;
    let fixture: ComponentFixture<DatabaseModelUpdateComponent>;
    let service: DatabaseModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [DatabaseModelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DatabaseModelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatabaseModelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatabaseModelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatabaseModel('123');
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
        const entity = new DatabaseModel();
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
