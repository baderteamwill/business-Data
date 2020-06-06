import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntityRelation } from 'app/shared/model/entity-relation.model';

type EntityResponseType = HttpResponse<IEntityRelation>;
type EntityArrayResponseType = HttpResponse<IEntityRelation[]>;

@Injectable({ providedIn: 'root' })
export class EntityRelationService {
  public resourceUrl = SERVER_API_URL + 'api/entity-relations';

  constructor(protected http: HttpClient) {}

  create(entityRelation: IEntityRelation): Observable<EntityResponseType> {
    return this.http.post<IEntityRelation>(this.resourceUrl, entityRelation, { observe: 'response' });
  }

  update(entityRelation: IEntityRelation): Observable<EntityResponseType> {
    return this.http.put<IEntityRelation>(this.resourceUrl, entityRelation, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEntityRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntityRelation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
