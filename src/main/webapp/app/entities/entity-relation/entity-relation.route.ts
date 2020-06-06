import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntityRelation, EntityRelation } from 'app/shared/model/entity-relation.model';
import { EntityRelationService } from './entity-relation.service';
import { EntityRelationComponent } from './entity-relation.component';
import { EntityRelationDetailComponent } from './entity-relation-detail.component';
import { EntityRelationUpdateComponent } from './entity-relation-update.component';

@Injectable({ providedIn: 'root' })
export class EntityRelationResolve implements Resolve<IEntityRelation> {
  constructor(private service: EntityRelationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntityRelation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entityRelation: HttpResponse<EntityRelation>) => {
          if (entityRelation.body) {
            return of(entityRelation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EntityRelation());
  }
}

export const entityRelationRoute: Routes = [
  {
    path: '',
    component: EntityRelationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityRelation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EntityRelationDetailComponent,
    resolve: {
      entityRelation: EntityRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityRelation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EntityRelationUpdateComponent,
    resolve: {
      entityRelation: EntityRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityRelation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EntityRelationUpdateComponent,
    resolve: {
      entityRelation: EntityRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityRelation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
