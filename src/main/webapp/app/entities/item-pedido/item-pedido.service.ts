import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ItemPedido } from './item-pedido.model';
import { Comanda } from '../comanda';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ItemPedidoService {

    private resourceUrl = SERVER_API_URL + 'api/item-pedidos';

    constructor(private http: Http) { }

    create(comanda: Comanda, itens: ItemPedido[]): Observable<ItemPedido> {
        return this.http.post(this.resourceUrl, { comanda, itens }).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ItemPedido> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(itemPedido: ItemPedido): ItemPedido {
        const copy: ItemPedido = Object.assign({}, itemPedido);
        return copy;
    }
}
