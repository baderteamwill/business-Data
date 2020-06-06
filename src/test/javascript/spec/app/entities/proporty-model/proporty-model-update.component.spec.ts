import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { ProportyModelUpdateComponent } from 'app/entities/proporty-model/proporty-model-update.component';
import { ProportyModelService } from 'app/entities/proporty-model/proporty-model.service';
import { ProportyModel } from 'app/shared/model/proporty-model.model';

describe('Component Tests', () => {
  describe('ProportyModel Management Update Component', () => {
    let comp: ProportyModelUpdateComponent;
    let fixture: ComponentFixture<ProportyModelUpdateComponent>;
    let service: ProportyModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [ProportyModelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProportyModelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProportyModelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProportyModelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProportyModel('123');
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
        const entity = new ProportyModel();
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
