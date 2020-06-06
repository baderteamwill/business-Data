import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatabaseModel } from 'app/shared/model/database-model.model';

type EntityResponseType = HttpResponse<IDatabaseModel>;
type EntityArrayResponseType = HttpResponse<IDatabaseModel[]>;

@Injectable({ providedIn: 'root' })
export class DatabaseModelService {
  public resourceUrl = SERVER_API_URL + 'api/database-models';

  constructor(protected http: HttpClient) {}

  create(databaseModel: IDatabaseModel): Observable<EntityResponseType> {
    return this.http.post<IDatabaseModel>(this.resourceUrl, databaseModel, { observe: 'response' });
  }

  update(databaseModel: IDatabaseModel): Observable<EntityResponseType> {
    return this.http.put<IDatabaseModel>(this.resourceUrl, databaseModel, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IDatabaseModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatabaseModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
