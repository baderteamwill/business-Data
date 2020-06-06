import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInstanceRelation, InstanceRelation } from 'app/shared/model/instance-relation.model';
import { InstanceRelationService } from './instance-relation.service';
import { InstanceRelationComponent } from './instance-relation.component';
import { InstanceRelationDetailComponent } from './instance-relation-detail.component';
import { InstanceRelationUpdateComponent } from './instance-relation-update.component';

@Injectable({ providedIn: 'root' })
export class InstanceRelationResolve implements Resolve<IInstanceRelation> {
  constructor(private service: InstanceRelationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInstanceRelation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((instanceRelation: HttpResponse<InstanceRelation>) => {
          if (instanceRelation.body) {
            return of(instanceRelation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InstanceRelation());
  }
}

export const instanceRelationRoute: Routes = [
  {
    path: '',
    component: InstanceRelationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.instanceRelation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InstanceRelationDetailComponent,
    resolve: {
      instanceRelation: InstanceRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.instanceRelation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InstanceRelationUpdateComponent,
    resolve: {
      instanceRelation: InstanceRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.instanceRelation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InstanceRelationUpdateComponent,
    resolve: {
      instanceRelation: InstanceRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.instanceRelation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
