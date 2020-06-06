import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BusinessDataSharedModule } from 'app/shared/shared.module';
import { EntityinstanceComponent } from './entityinstance.component';
import { EntityinstanceDetailComponent } from './entityinstance-detail.component';
import { EntityinstanceUpdateComponent } from './entityinstance-update.component';
import { EntityinstanceDeleteDialogComponent } from './entityinstance-delete-dialog.component';
import { entityinstanceRoute } from './entityinstance.route';

@NgModule({
  imports: [BusinessDataSharedModule, RouterModule.forChild(entityinstanceRoute)],
  declarations: [
    EntityinstanceComponent,
    EntityinstanceDetailComponent,
    EntityinstanceUpdateComponent,
    EntityinstanceDeleteDialogComponent
  ],
  entryComponents: [EntityinstanceDeleteDialogComponent]
})
export class BusinessDataEntityinstanceModule {}
