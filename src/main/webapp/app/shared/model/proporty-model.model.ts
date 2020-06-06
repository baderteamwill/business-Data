import { IEntityModel } from 'app/shared/model/entity-model.model';
import { IProportyData } from 'app/shared/model/proporty-data.model';
import { Type } from 'app/shared/model/enumerations/type.model';

export interface IProportyModel {
  id?: string;
  proportyName?: string;
  proportyType?: Type;
  entityModel?: IEntityModel;
  proportyData?: IProportyData;
}

export class ProportyModel implements IProportyModel {
  constructor(
    public id?: string,
    public proportyName?: string,
    public proportyType?: Type,
    public entityModel?: IEntityModel,
    public proportyData?: IProportyData
  ) {}
}
