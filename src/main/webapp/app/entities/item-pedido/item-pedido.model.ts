import { BaseEntity } from './../../shared';

import { Produto } from '../produto';

export class ItemPedido implements BaseEntity {
    constructor(
        public id?: number,
        public quantidade?: number,
        public pedido?: BaseEntity,
        public produto?: Produto,
    ) { }
}
