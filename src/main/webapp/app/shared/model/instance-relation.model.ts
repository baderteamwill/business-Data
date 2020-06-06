import { IEntityInstance } from 'app/shared/model/entity-instance.model';
import { IEntityRelation } from 'app/shared/model/entity-relation.model';

export interface IInstanceRelation {
  id?: string;
  entityInstance?: IEntityInstance;
  entityInstance2?: IEntityInstance;
  entityRelation?: IEntityRelation;
}

export class InstanceRelation implements IInstanceRelation {
  constructor(
    public id?: string,
    public entityInstance?: IEntityInstance,
    public entityInstance2?: IEntityInstance,
    public entityRelation?: IEntityRelation
  ) {}
}
