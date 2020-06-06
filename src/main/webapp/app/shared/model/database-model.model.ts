import { IEntityModel } from 'app/shared/model/entity-model.model';

export interface IDatabaseModel {
  id?: string;
  databaseModel?: string;
  databaseDescription?: string;
  entityModels?: IEntityModel[];
}

export class DatabaseModel implements IDatabaseModel {
  constructor(
    public id?: string,
    public databaseModel?: string,
    public databaseDescription?: string,
    public entityModels?: IEntityModel[]
  ) {}
}
