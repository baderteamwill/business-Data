import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'database-model',
        loadChildren: () => import('./database-model/database-model.module').then(m => m.BusinessDataDatabaseModelModule)
      },
      {
        path: 'entity-model',
        loadChildren: () => import('./entity-model/entity-model.module').then(m => m.BusinessDataEntityModelModule)
      },
      {
        path: 'proporty-model',
        loadChildren: () => import('./proporty-model/proporty-model.module').then(m => m.BusinessDataProportyModelModule)
      },
      {
        path: 'proporty-data',
        loadChildren: () => import('./proporty-data/proporty-data.module').then(m => m.BusinessDataProportyDataModule)
      },
      {
        path: 'entity-instance',
        loadChildren: () => import('./entity-instance/entity-instance.module').then(m => m.BusinessDataEntityInstanceModule)
      },
      {
        path: 'entityinstance',
        loadChildren: () => import('./entityinstance/entityinstance.module').then(m => m.BusinessDataEntityinstanceModule)
      },
      {
        path: 'entity-relation',
        loadChildren: () => import('./entity-relation/entity-relation.module').then(m => m.BusinessDataEntityRelationModule)
      },
      {
        path: 'instance-relation',
        loadChildren: () => import('./instance-relation/instance-relation.module').then(m => m.BusinessDataInstanceRelationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BusinessDataEntityModule {}
