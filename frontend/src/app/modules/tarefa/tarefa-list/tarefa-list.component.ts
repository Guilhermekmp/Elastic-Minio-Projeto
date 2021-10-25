import { StatusEnum } from './../../../shared/enumeration/status-enum';
import { TarefaService } from './../../../shared/services/tarefa.service';
import { MensagemUtil } from '../../../shared/util/mensagem-util';
import { DefaultFilter } from '../../../shared/model/default-filter';
import { Page } from '../../../shared/model/page';
import { Tarefa } from '../model/tarefa';
import { Component, OnInit } from '@angular/core';
import { Table } from 'primeng';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-tarefa-list',
  templateUrl: './tarefa-list.component.html',
  styleUrls: ['./tarefa-list.component.css']
})
export class TarefaListComponent implements OnInit {

  @BlockUI() blockUI: NgBlockUI;

  tarefas = new Page<Tarefa>();
  resultado: Table;
  tarefaFiltro = new DefaultFilter();

  constructor(
      private tarefaService: TarefaService) { }

  ngOnInit(): void {
      this.carregarTabela();
  }

  carregarTabela(){
    this.blockUI.start(MensagemUtil.BLOCKUI_CARREGANDO);
    this.tarefaService.pesquisar(this.tarefaFiltro,this.resultado)
        .pipe(finalize(() => this.blockUI.stop()))
        .subscribe(page =>{ this.tarefas = page;
            console.log(page);
        });
  }

  statusTarefa(status: boolean): string{
    if(status){
        return StatusEnum.ATIVO;
    }
    return StatusEnum.INATIVO
  }
}
