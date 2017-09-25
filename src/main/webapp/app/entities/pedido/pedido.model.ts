import { BaseEntity } from './../../shared';

export class Pedido implements BaseEntity {
    constructor(
        public id?: number,
        public data?: any,
        public comanda?: BaseEntity,
    ) {
    }
}
