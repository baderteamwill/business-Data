import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BusinessDataTestModule } from '../../../test.module';
import { ProportyDataComponent } from 'app/entities/proporty-data/proporty-data.component';
import { ProportyDataService } from 'app/entities/proporty-data/proporty-data.service';
import { ProportyData } from 'app/shared/model/proporty-data.model';

describe('Component Tests', () => {
  describe('ProportyData Management Component', () => {
    let comp: ProportyDataComponent;
    let fixture: ComponentFixture<ProportyDataComponent>;
    let service: ProportyDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [ProportyDataComponent],
        providers: []
      })
        .overrideTemplate(ProportyDataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProportyDataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProportyDataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProportyData('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proportyData && comp.proportyData[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
