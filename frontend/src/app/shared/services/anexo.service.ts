import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Table } from 'primeng';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DefaultFilter } from '../model/default-filter';
import { Page } from '../model/page';
import { RequestUtil } from '../util/request-util';
import { Anexo } from './../../modules/anexo/model/anexo';

@Injectable()
export class AnexoService{
    readonly resourceUrl = `${environment.apiUrl}/anexos`;

    constructor(private httpClient: HttpClient) {}

    pesquisar(filter: DefaultFilter, dataTable: Table): Observable<Page<Anexo>> {
        return this.httpClient.post<Page<Anexo>>(`${this.resourceUrl}/pesquisar`, filter, {
            params: RequestUtil.getRequestParamsTable(dataTable)
        });
    }
}
