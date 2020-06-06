import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDatabaseModel, DatabaseModel } from 'app/shared/model/database-model.model';
import { DatabaseModelService } from './database-model.service';
import { DatabaseModelComponent } from './database-model.component';
import { DatabaseModelDetailComponent } from './database-model-detail.component';
import { DatabaseModelUpdateComponent } from './database-model-update.component';

@Injectable({ providedIn: 'root' })
export class DatabaseModelResolve implements Resolve<IDatabaseModel> {
  constructor(private service: DatabaseModelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDatabaseModel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((databaseModel: HttpResponse<DatabaseModel>) => {
          if (databaseModel.body) {
            return of(databaseModel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DatabaseModel());
  }
}

export const databaseModelRoute: Routes = [
  {
    path: '',
    component: DatabaseModelComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.databaseModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DatabaseModelDetailComponent,
    resolve: {
      databaseModel: DatabaseModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.databaseModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DatabaseModelUpdateComponent,
    resolve: {
      databaseModel: DatabaseModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.databaseModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DatabaseModelUpdateComponent,
    resolve: {
      databaseModel: DatabaseModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.databaseModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
