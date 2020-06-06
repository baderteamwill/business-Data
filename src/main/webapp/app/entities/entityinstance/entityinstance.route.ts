import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntityinstance, Entityinstance } from 'app/shared/model/entityinstance.model';
import { EntityinstanceService } from './entityinstance.service';
import { EntityinstanceComponent } from './entityinstance.component';
import { EntityinstanceDetailComponent } from './entityinstance-detail.component';
import { EntityinstanceUpdateComponent } from './entityinstance-update.component';

@Injectable({ providedIn: 'root' })
export class EntityinstanceResolve implements Resolve<IEntityinstance> {
  constructor(private service: EntityinstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntityinstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entityinstance: HttpResponse<Entityinstance>) => {
          if (entityinstance.body) {
            return of(entityinstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Entityinstance());
  }
}

export const entityinstanceRoute: Routes = [
  {
    path: '',
    component: EntityinstanceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityinstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EntityinstanceDetailComponent,
    resolve: {
      entityinstance: EntityinstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityinstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EntityinstanceUpdateComponent,
    resolve: {
      entityinstance: EntityinstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityinstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EntityinstanceUpdateComponent,
    resolve: {
      entityinstance: EntityinstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityinstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
