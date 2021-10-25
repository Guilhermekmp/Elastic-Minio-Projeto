import { AnexoService } from './anexo.service';
import { TarefaService } from './tarefa.service';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResponsavelService } from './responsavel.service';

@NgModule({
    declarations: [],
    imports: [
      CommonModule
    ],
    providers: [
        ResponsavelService,
        TarefaService,
        AnexoService
    ]
  })
  export class ServiceModule { }
