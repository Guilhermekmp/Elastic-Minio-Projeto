import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { AnexoService } from './../../shared/services/anexo.service';
import { AnexoListComponent } from './anexo-list/anexo-list.component';
import { AnexoRoutingModule } from './anexo-routing.module';



@NgModule({
  declarations: [AnexoListComponent],
  imports: [
    SharedModule,
    AnexoRoutingModule
  ],
  providers: [
      AnexoService
  ]
})
export class AnexoModule { }
