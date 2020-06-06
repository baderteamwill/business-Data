import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntityInstance } from 'app/shared/model/entity-instance.model';

type EntityResponseType = HttpResponse<IEntityInstance>;
type EntityArrayResponseType = HttpResponse<IEntityInstance[]>;

@Injectable({ providedIn: 'root' })
export class EntityInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/entity-instances';

  constructor(protected http: HttpClient) {}

  create(entityInstance: IEntityInstance): Observable<EntityResponseType> {
    return this.http.post<IEntityInstance>(this.resourceUrl, entityInstance, { observe: 'response' });
  }

  update(entityInstance: IEntityInstance): Observable<EntityResponseType> {
    return this.http.put<IEntityInstance>(this.resourceUrl, entityInstance, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEntityInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntityInstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
