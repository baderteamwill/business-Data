import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInstanceRelation } from 'app/shared/model/instance-relation.model';

type EntityResponseType = HttpResponse<IInstanceRelation>;
type EntityArrayResponseType = HttpResponse<IInstanceRelation[]>;

@Injectable({ providedIn: 'root' })
export class InstanceRelationService {
  public resourceUrl = SERVER_API_URL + 'api/instance-relations';

  constructor(protected http: HttpClient) {}

  create(instanceRelation: IInstanceRelation): Observable<EntityResponseType> {
    return this.http.post<IInstanceRelation>(this.resourceUrl, instanceRelation, { observe: 'response' });
  }

  update(instanceRelation: IInstanceRelation): Observable<EntityResponseType> {
    return this.http.put<IInstanceRelation>(this.resourceUrl, instanceRelation, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IInstanceRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInstanceRelation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
