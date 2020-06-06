import { IEntityInstance } from 'app/shared/model/entity-instance.model';
import { IProportyModel } from 'app/shared/model/proporty-model.model';

export interface IProportyData {
  id?: string;
  proportyValue?: string;
  entityInstance?: IEntityInstance;
  proportyModel?: IProportyModel;
}

export class ProportyData implements IProportyData {
  constructor(
    public id?: string,
    public proportyValue?: string,
    public entityInstance?: IEntityInstance,
    public proportyModel?: IProportyModel
  ) {}
}
