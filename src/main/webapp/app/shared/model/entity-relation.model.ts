import { IEntityModel } from 'app/shared/model/entity-model.model';
import { IInstanceRelation } from 'app/shared/model/instance-relation.model';
import { RelationType } from 'app/shared/model/enumerations/relation-type.model';

export interface IEntityRelation {
  id?: string;
  relation?: RelationType;
  entityModel?: IEntityModel;
  entityModel2?: IEntityModel;
  instanceRelations?: IInstanceRelation[];
}

export class EntityRelation implements IEntityRelation {
  constructor(
    public id?: string,
    public relation?: RelationType,
    public entityModel?: IEntityModel,
    public entityModel2?: IEntityModel,
    public instanceRelations?: IInstanceRelation[]
  ) {}
}
