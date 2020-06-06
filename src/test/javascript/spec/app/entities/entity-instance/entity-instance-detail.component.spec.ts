import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityInstanceDetailComponent } from 'app/entities/entity-instance/entity-instance-detail.component';
import { EntityInstance } from 'app/shared/model/entity-instance.model';

describe('Component Tests', () => {
  describe('EntityInstance Management Detail Component', () => {
    let comp: EntityInstanceDetailComponent;
    let fixture: ComponentFixture<EntityInstanceDetailComponent>;
    const route = ({ data: of({ entityInstance: new EntityInstance('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntityInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntityInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entityInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entityInstance).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
