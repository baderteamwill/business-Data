import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProportyData, ProportyData } from 'app/shared/model/proporty-data.model';
import { ProportyDataService } from './proporty-data.service';
import { ProportyDataComponent } from './proporty-data.component';
import { ProportyDataDetailComponent } from './proporty-data-detail.component';
import { ProportyDataUpdateComponent } from './proporty-data-update.component';

@Injectable({ providedIn: 'root' })
export class ProportyDataResolve implements Resolve<IProportyData> {
  constructor(private service: ProportyDataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProportyData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((proportyData: HttpResponse<ProportyData>) => {
          if (proportyData.body) {
            return of(proportyData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProportyData());
  }
}

export const proportyDataRoute: Routes = [
  {
    path: '',
    component: ProportyDataComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.proportyData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProportyDataDetailComponent,
    resolve: {
      proportyData: ProportyDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.proportyData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProportyDataUpdateComponent,
    resolve: {
      proportyData: ProportyDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.proportyData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProportyDataUpdateComponent,
    resolve: {
      proportyData: ProportyDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'businessDataApp.proportyData.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
