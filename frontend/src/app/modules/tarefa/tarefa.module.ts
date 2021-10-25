import { TarefaService } from './../../shared/services/tarefa.service';
import { SharedModule } from '../../shared/shared.module';
import { TarefaRoutingModule } from './tarefa-routing.module';
import { NgModule } from '@angular/core';
import { TarefaListComponent } from './tarefa-list/tarefa-list.component';



@NgModule({
  declarations: [TarefaListComponent],
  imports: [
    SharedModule,
    TarefaRoutingModule
  ],
  providers: [
      TarefaService
  ]
})
export class TarefaModule { }
