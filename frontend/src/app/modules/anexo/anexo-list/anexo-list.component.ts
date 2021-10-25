import { AnexoService } from './../../../shared/services/anexo.service';
import { MensagemUtil } from '../../../shared/util/mensagem-util';
import { DefaultFilter } from '../../../shared/model/default-filter';
import { Page } from '../../../shared/model/page';
import { Anexo } from '../model/anexo';
import { Component, OnInit } from '@angular/core';
import { Table } from 'primeng';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-anexo-list',
  templateUrl: './anexo-list.component.html',
  styleUrls: ['./anexo-list.component.css']
})
export class AnexoListComponent implements OnInit {

  @BlockUI() blockUI: NgBlockUI;

  anexos = new Page<Anexo>();
  resultado: Table;
  anexoFiltro = new DefaultFilter();

  constructor(
      private anexoService: AnexoService) { }

  ngOnInit(): void {
      this.carregarTabela();
  }

  carregarTabela(){
    this.blockUI.start(MensagemUtil.BLOCKUI_CARREGANDO);
    this.anexoService.pesquisar(this.anexoFiltro,this.resultado)
        .pipe(finalize(() => this.blockUI.stop()))
        .subscribe(page => this.anexos = page);
  }

}
