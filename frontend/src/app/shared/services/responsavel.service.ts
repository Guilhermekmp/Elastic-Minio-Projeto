import { RequestUtil } from './../util/request-util';
import { Observable } from 'rxjs';
import { DefaultFilter } from './../model/default-filter';
import { Page } from './../model/page';
import { environment } from './../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Responsavel } from 'src/app/modules/responsavel/model/responsavel';
import { Table } from 'primeng';

@Injectable()
export class ResponsavelService{
    readonly resourceUrl = `${environment.apiUrl}/responsaveis`;

    constructor(private httpClient: HttpClient) {}

    pesquisar(filter: DefaultFilter, dataTable: Table): Observable<Page<Responsavel>> {
        return this.httpClient.post<Page<Responsavel>>(`${this.resourceUrl}/pesquisar`, filter, {
            params: RequestUtil.getRequestParamsTable(dataTable)
        });
    }
}
