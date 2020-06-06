import { IDatabaseModel } from 'app/shared/model/database-model.model';
import { IProportyModel } from 'app/shared/model/proporty-model.model';
import { IEntityInstance } from 'app/shared/model/entity-instance.model';
import { IEntityRelation } from 'app/shared/model/entity-relation.model';

export interface IEntityModel {
  id?: string;
  entityName?: string;
  entityDescription?: string;
  databaseModel?: IDatabaseModel;
  proportyModels?: IProportyModel[];
  entityInstances?: IEntityInstance[];
  entityRelation?: IEntityRelation;
  entityRelation2?: IEntityRelation;
}

export class EntityModel implements IEntityModel {
  constructor(
    public id?: string,
    public entityName?: string,
    public entityDescription?: string,
    public databaseModel?: IDatabaseModel,
    public proportyModels?: IProportyModel[],
    public entityInstances?: IEntityInstance[],
    public entityRelation?: IEntityRelation,
    public entityRelation2?: IEntityRelation
  ) {}
}
