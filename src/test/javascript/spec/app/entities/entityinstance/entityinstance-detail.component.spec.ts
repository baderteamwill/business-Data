import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityinstanceDetailComponent } from 'app/entities/entityinstance/entityinstance-detail.component';
import { Entityinstance } from 'app/shared/model/entityinstance.model';

describe('Component Tests', () => {
  describe('Entityinstance Management Detail Component', () => {
    let comp: EntityinstanceDetailComponent;
    let fixture: ComponentFixture<EntityinstanceDetailComponent>;
    const route = ({ data: of({ entityinstance: new Entityinstance('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityinstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntityinstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntityinstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entityinstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entityinstance).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
