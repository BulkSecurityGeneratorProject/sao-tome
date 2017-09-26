import { BaseEntity } from './../../shared';

export class ItemPedido implements BaseEntity {
    constructor(
        public id?: number,
        public quantidade?: number,
        public pedido?: BaseEntity,
        public produto?: BaseEntity,
    ) {
    }
}
