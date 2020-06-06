import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntityInstance, EntityInstance } from 'app/shared/model/entity-instance.model';
import { EntityInstanceService } from './entity-instance.service';
import { EntityInstanceComponent } from './entity-instance.component';
import { EntityInstanceDetailComponent } from './entity-instance-detail.component';
import { EntityInstanceUpdateComponent } from './entity-instance-update.component';

@Injectable({ providedIn: 'root' })
export class EntityInstanceResolve implements Resolve<IEntityInstance> {
  constructor(private service: EntityInstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntityInstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entityInstance: HttpResponse<EntityInstance>) => {
          if (entityInstance.body) {
            return of(entityInstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EntityInstance());
  }
}

export const entityInstanceRoute: Routes = [
  {
    path: '',
    component: EntityInstanceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EntityInstanceDetailComponent,
    resolve: {
      entityInstance: EntityInstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EntityInstanceUpdateComponent,
    resolve: {
      entityInstance: EntityInstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EntityInstanceUpdateComponent,
    resolve: {
      entityInstance: EntityInstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
