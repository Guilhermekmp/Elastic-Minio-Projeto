import { ResponsavelService } from './../../shared/services/responsavel.service';
import { SharedModule } from './../../shared/shared.module';
import { ResponsavelRoutingModule } from './responsavel-routing.module';
import { NgModule } from '@angular/core';
import { ResponsavelListComponent } from './responsavel-list/responsavel-list.component';



@NgModule({
  declarations: [ResponsavelListComponent],
  imports: [
    SharedModule,
    ResponsavelRoutingModule
  ],
  providers: [
      ResponsavelService
  ]
})
export class ResponsavelModule { }
