import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntityModel } from 'app/shared/model/entity-model.model';

type EntityResponseType = HttpResponse<IEntityModel>;
type EntityArrayResponseType = HttpResponse<IEntityModel[]>;

@Injectable({ providedIn: 'root' })
export class EntityModelService {
  public resourceUrl = SERVER_API_URL + 'api/entity-models';

  constructor(protected http: HttpClient) {}

  create(entityModel: IEntityModel): Observable<EntityResponseType> {
    return this.http.post<IEntityModel>(this.resourceUrl, entityModel, { observe: 'response' });
  }

  update(entityModel: IEntityModel): Observable<EntityResponseType> {
    return this.http.put<IEntityModel>(this.resourceUrl, entityModel, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEntityModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntityModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
