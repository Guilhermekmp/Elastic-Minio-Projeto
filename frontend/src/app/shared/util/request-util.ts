import { HttpParams } from '@angular/common/http';
import { Table } from 'primeng';

export class RequestUtil {

    static getRequestParamsTable = (datatable: Table): HttpParams => {
        let params: HttpParams = new HttpParams();
        if (datatable == null) {
            params = params.append('size', '10');
            return params;
        }

        params = params.append('page', Math.round(datatable.first / datatable.rows).toString());
        params = params.append('size', datatable.rows == null ? '10' : datatable.rows.toString());

        const direction = datatable.sortOrder === 1 ? 'ASC' : 'DESC';
        params = params.append('sort', datatable.sortField == null ? '' : datatable.sortField + ',' + direction);

        return params;
    }

    static getRequestParams = (objeto: Object): HttpParams => {

        let params: HttpParams = new HttpParams();

        if (!!objeto) {
            Object.keys(objeto).forEach(param => {
                if (objeto[param] !== null) {
                    params = params.append(param, objeto[param]);
                }
            });
        }

        return params;
    }

}
