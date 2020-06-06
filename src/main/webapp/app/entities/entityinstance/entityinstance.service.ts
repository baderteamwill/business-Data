import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntityinstance } from 'app/shared/model/entityinstance.model';

type EntityResponseType = HttpResponse<IEntityinstance>;
type EntityArrayResponseType = HttpResponse<IEntityinstance[]>;

@Injectable({ providedIn: 'root' })
export class EntityinstanceService {
  public resourceUrl = SERVER_API_URL + 'api/entityinstances';

  constructor(protected http: HttpClient) {}

  create(entityinstance: IEntityinstance): Observable<EntityResponseType> {
    return this.http.post<IEntityinstance>(this.resourceUrl, entityinstance, { observe: 'response' });
  }

  update(entityinstance: IEntityinstance): Observable<EntityResponseType> {
    return this.http.put<IEntityinstance>(this.resourceUrl, entityinstance, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEntityinstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntityinstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
