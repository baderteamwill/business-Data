import { IProportyData } from 'app/shared/model/proporty-data.model';
import { IEntityModel } from 'app/shared/model/entity-model.model';
import { IInstanceRelation } from 'app/shared/model/instance-relation.model';

export interface IEntityInstance {
  id?: string;
  instanceName?: string;
  proportyData?: IProportyData[];
  entityModel?: IEntityModel;
  instanceRelation?: IInstanceRelation;
  instanceRelation2?: IInstanceRelation;
}

export class EntityInstance implements IEntityInstance {
  constructor(
    public id?: string,
    public instanceName?: string,
    public proportyData?: IProportyData[],
    public entityModel?: IEntityModel,
    public instanceRelation?: IInstanceRelation,
    public instanceRelation2?: IInstanceRelation
  ) {}
}
