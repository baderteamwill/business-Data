import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProportyModel } from 'app/shared/model/proporty-model.model';

type EntityResponseType = HttpResponse<IProportyModel>;
type EntityArrayResponseType = HttpResponse<IProportyModel[]>;

@Injectable({ providedIn: 'root' })
export class ProportyModelService {
  public resourceUrl = SERVER_API_URL + 'api/proporty-models';

  constructor(protected http: HttpClient) {}

  create(proportyModel: IProportyModel): Observable<EntityResponseType> {
    return this.http.post<IProportyModel>(this.resourceUrl, proportyModel, { observe: 'response' });
  }

  update(proportyModel: IProportyModel): Observable<EntityResponseType> {
    return this.http.put<IProportyModel>(this.resourceUrl, proportyModel, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProportyModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProportyModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
