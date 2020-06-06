import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BusinessDataTestModule } from '../../../test.module';
import { ProportyModelComponent } from 'app/entities/proporty-model/proporty-model.component';
import { ProportyModelService } from 'app/entities/proporty-model/proporty-model.service';
import { ProportyModel } from 'app/shared/model/proporty-model.model';

describe('Component Tests', () => {
  describe('ProportyModel Management Component', () => {
    let comp: ProportyModelComponent;
    let fixture: ComponentFixture<ProportyModelComponent>;
    let service: ProportyModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [ProportyModelComponent],
        providers: []
      })
        .overrideTemplate(ProportyModelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProportyModelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProportyModelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProportyModel('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proportyModels && comp.proportyModels[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
