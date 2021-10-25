import { MensagemUtil } from './../../../shared/util/mensagem-util';
import { DefaultFilter } from './../../../shared/model/default-filter';
import { ResponsavelService } from './../../../shared/services/responsavel.service';
import { Page } from './../../../shared/model/page';
import { Responsavel } from './../model/responsavel';
import { Component, OnInit } from '@angular/core';
import { Table } from 'primeng';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-responsavel-list',
  templateUrl: './responsavel-list.component.html',
  styleUrls: ['./responsavel-list.component.css']
})
export class ResponsavelListComponent implements OnInit {

  @BlockUI() blockUI: NgBlockUI;

  responsaveis = new Page<Responsavel>();
  resultado: Table;
  responsavelFiltro = new DefaultFilter();

  constructor(
      private responsavelService: ResponsavelService) { }

  ngOnInit(): void {
      this.carregarTabela();
  }

  carregarTabela(){
    this.blockUI.start(MensagemUtil.BLOCKUI_CARREGANDO);
    this.responsavelService.pesquisar(this.responsavelFiltro,this.resultado)
        .pipe(finalize(() => this.blockUI.stop()))
        .subscribe(page => this.responsaveis = page);
  }

}
