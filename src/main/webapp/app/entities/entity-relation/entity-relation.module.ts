import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BusinessDataSharedModule } from 'app/shared/shared.module';
import { EntityRelationComponent } from './entity-relation.component';
import { EntityRelationDetailComponent } from './entity-relation-detail.component';
import { EntityRelationUpdateComponent } from './entity-relation-update.component';
import { EntityRelationDeleteDialogComponent } from './entity-relation-delete-dialog.component';
import { entityRelationRoute } from './entity-relation.route';

@NgModule({
  imports: [BusinessDataSharedModule, RouterModule.forChild(entityRelationRoute)],
  declarations: [
    EntityRelationComponent,
    EntityRelationDetailComponent,
    EntityRelationUpdateComponent,
    EntityRelationDeleteDialogComponent
  ],
  entryComponents: [EntityRelationDeleteDialogComponent]
})
export class BusinessDataEntityRelationModule {}
