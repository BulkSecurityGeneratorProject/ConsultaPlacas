import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Corporacion } from './corporacion.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CorporacionService {

    private resourceUrl = SERVER_API_URL + 'api/corporacions';

    constructor(private http: Http) { }

    create(corporacion: Corporacion): Observable<Corporacion> {
        const copy = this.convert(corporacion);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(corporacion: Corporacion): Observable<Corporacion> {
        const copy = this.convert(corporacion);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Corporacion> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Corporacion.
     */
    private convertItemFromServer(json: any): Corporacion {
        const entity: Corporacion = Object.assign(new Corporacion(), json);
        return entity;
    }

    /**
     * Convert a Corporacion to a JSON which can be sent to the server.
     */
    private convert(corporacion: Corporacion): Corporacion {
        const copy: Corporacion = Object.assign({}, corporacion);
        return copy;
    }
}
