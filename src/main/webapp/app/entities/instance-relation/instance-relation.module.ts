import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BusinessDataSharedModule } from 'app/shared/shared.module';
import { InstanceRelationComponent } from './instance-relation.component';
import { InstanceRelationDetailComponent } from './instance-relation-detail.component';
import { InstanceRelationUpdateComponent } from './instance-relation-update.component';
import { InstanceRelationDeleteDialogComponent } from './instance-relation-delete-dialog.component';
import { instanceRelationRoute } from './instance-relation.route';

@NgModule({
  imports: [BusinessDataSharedModule, RouterModule.forChild(instanceRelationRoute)],
  declarations: [
    InstanceRelationComponent,
    InstanceRelationDetailComponent,
    InstanceRelationUpdateComponent,
    InstanceRelationDeleteDialogComponent
  ],
  entryComponents: [InstanceRelationDeleteDialogComponent]
})
export class BusinessDataInstanceRelationModule {}
