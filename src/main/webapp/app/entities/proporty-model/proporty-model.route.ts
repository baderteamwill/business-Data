import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProportyModel, ProportyModel } from 'app/shared/model/proporty-model.model';
import { ProportyModelService } from './proporty-model.service';
import { ProportyModelComponent } from './proporty-model.component';
import { ProportyModelDetailComponent } from './proporty-model-detail.component';
import { ProportyModelUpdateComponent } from './proporty-model-update.component';

@Injectable({ providedIn: 'root' })
export class ProportyModelResolve implements Resolve<IProportyModel> {
  constructor(private service: ProportyModelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProportyModel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((proportyModel: HttpResponse<ProportyModel>) => {
          if (proportyModel.body) {
            return of(proportyModel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProportyModel());
  }
}

export const proportyModelRoute: Routes = [
  {
    path: '',
    component: ProportyModelComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.proportyModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProportyModelDetailComponent,
    resolve: {
      proportyModel: ProportyModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.proportyModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProportyModelUpdateComponent,
    resolve: {
      proportyModel: ProportyModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.proportyModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProportyModelUpdateComponent,
    resolve: {
      proportyModel: ProportyModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.proportyModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
