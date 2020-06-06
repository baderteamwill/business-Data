import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BusinessDataSharedModule } from 'app/shared/shared.module';
import { EntityInstanceComponent } from './entity-instance.component';
import { EntityInstanceDetailComponent } from './entity-instance-detail.component';
import { EntityInstanceUpdateComponent } from './entity-instance-update.component';
import { EntityInstanceDeleteDialogComponent } from './entity-instance-delete-dialog.component';
import { entityInstanceRoute } from './entity-instance.route';

@NgModule({
  imports: [BusinessDataSharedModule, RouterModule.forChild(entityInstanceRoute)],
  declarations: [
    EntityInstanceComponent,
    EntityInstanceDetailComponent,
    EntityInstanceUpdateComponent,
    EntityInstanceDeleteDialogComponent
  ],
  entryComponents: [EntityInstanceDeleteDialogComponent]
})
export class BusinessDataEntityInstanceModule {}
