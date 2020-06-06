import { IEntityModel } from 'app/shared/model/entity-model.model';

export interface IEntityinstance {
  id?: string;
  instanceName?: string;
  entityModel?: IEntityModel;
}

export class Entityinstance implements IEntityinstance {
  constructor(public id?: string, public instanceName?: string, public entityModel?: IEntityModel) {}
}
