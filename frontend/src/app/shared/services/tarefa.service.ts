import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Table } from 'primeng';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DefaultFilter } from '../model/default-filter';
import { Page } from '../model/page';
import { RequestUtil } from '../util/request-util';
import { Tarefa } from './../../modules/tarefa/model/tarefa';

@Injectable()
export class TarefaService{
    readonly resourceUrl = `${environment.apiUrl}/tarefas`;

    constructor(private httpClient: HttpClient) {}

    pesquisar(filter: DefaultFilter, dataTable: Table): Observable<Page<Tarefa>> {
        return this.httpClient.post<Page<Tarefa>>(`${this.resourceUrl}/pesquisar`, filter, {
            params: RequestUtil.getRequestParamsTable(dataTable)
        });
    }
}
