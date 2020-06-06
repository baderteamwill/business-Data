import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BusinessDataSharedModule } from 'app/shared/shared.module';
import { DatabaseModelComponent } from './database-model.component';
import { DatabaseModelDetailComponent } from './database-model-detail.component';
import { DatabaseModelUpdateComponent } from './database-model-update.component';
import { DatabaseModelDeleteDialogComponent } from './database-model-delete-dialog.component';
import { databaseModelRoute } from './database-model.route';

@NgModule({
  imports: [BusinessDataSharedModule, RouterModule.forChild(databaseModelRoute)],
  declarations: [DatabaseModelComponent, DatabaseModelDetailComponent, DatabaseModelUpdateComponent, DatabaseModelDeleteDialogComponent],
  entryComponents: [DatabaseModelDeleteDialogComponent]
})
export class BusinessDataDatabaseModelModule {}
