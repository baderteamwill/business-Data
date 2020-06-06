import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BusinessDataSharedModule } from 'app/shared/shared.module';
import { EntityModelComponent } from './entity-model.component';
import { EntityModelDetailComponent } from './entity-model-detail.component';
import { EntityModelUpdateComponent } from './entity-model-update.component';
import { EntityModelDeleteDialogComponent } from './entity-model-delete-dialog.component';
import { entityModelRoute } from './entity-model.route';

@NgModule({
  imports: [BusinessDataSharedModule, RouterModule.forChild(entityModelRoute)],
  declarations: [EntityModelComponent, EntityModelDetailComponent, EntityModelUpdateComponent, EntityModelDeleteDialogComponent],
  entryComponents: [EntityModelDeleteDialogComponent]
})
export class BusinessDataEntityModelModule {}
