import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { ProportyDataUpdateComponent } from 'app/entities/proporty-data/proporty-data-update.component';
import { ProportyDataService } from 'app/entities/proporty-data/proporty-data.service';
import { ProportyData } from 'app/shared/model/proporty-data.model';

describe('Component Tests', () => {
  describe('ProportyData Management Update Component', () => {
    let comp: ProportyDataUpdateComponent;
    let fixture: ComponentFixture<ProportyDataUpdateComponent>;
    let service: ProportyDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [ProportyDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProportyDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProportyDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProportyDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProportyData('123');
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
        const entity = new ProportyData();
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
