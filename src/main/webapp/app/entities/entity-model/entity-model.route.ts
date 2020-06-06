import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntityModel, EntityModel } from 'app/shared/model/entity-model.model';
import { EntityModelService } from './entity-model.service';
import { EntityModelComponent } from './entity-model.component';
import { EntityModelDetailComponent } from './entity-model-detail.component';
import { EntityModelUpdateComponent } from './entity-model-update.component';

@Injectable({ providedIn: 'root' })
export class EntityModelResolve implements Resolve<IEntityModel> {
  constructor(private service: EntityModelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntityModel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entityModel: HttpResponse<EntityModel>) => {
          if (entityModel.body) {
            return of(entityModel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EntityModel());
  }
}

export const entityModelRoute: Routes = [
  {
    path: '',
    component: EntityModelComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EntityModelDetailComponent,
    resolve: {
      entityModel: EntityModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EntityModelUpdateComponent,
    resolve: {
      entityModel: EntityModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EntityModelUpdateComponent,
    resolve: {
      entityModel: EntityModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.entityModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
