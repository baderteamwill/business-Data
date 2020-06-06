import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BusinessDataSharedModule } from 'app/shared/shared.module';
import { ProportyDataComponent } from './proporty-data.component';
import { ProportyDataDetailComponent } from './proporty-data-detail.component';
import { ProportyDataUpdateComponent } from './proporty-data-update.component';
import { ProportyDataDeleteDialogComponent } from './proporty-data-delete-dialog.component';
import { proportyDataRoute } from './proporty-data.route';

@NgModule({
  imports: [BusinessDataSharedModule, RouterModule.forChild(proportyDataRoute)],
  declarations: [ProportyDataComponent, ProportyDataDetailComponent, ProportyDataUpdateComponent, ProportyDataDeleteDialogComponent],
  entryComponents: [ProportyDataDeleteDialogComponent]
})
export class BusinessDataProportyDataModule {}
