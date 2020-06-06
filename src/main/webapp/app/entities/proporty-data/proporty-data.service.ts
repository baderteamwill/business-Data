import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProportyData } from 'app/shared/model/proporty-data.model';

type EntityResponseType = HttpResponse<IProportyData>;
type EntityArrayResponseType = HttpResponse<IProportyData[]>;

@Injectable({ providedIn: 'root' })
export class ProportyDataService {
  public resourceUrl = SERVER_API_URL + 'api/proporty-data';

  constructor(protected http: HttpClient) {}

  create(proportyData: IProportyData): Observable<EntityResponseType> {
    return this.http.post<IProportyData>(this.resourceUrl, proportyData, { observe: 'response' });
  }

  update(proportyData: IProportyData): Observable<EntityResponseType> {
    return this.http.put<IProportyData>(this.resourceUrl, proportyData, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProportyData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProportyData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
