import { BaseEntity } from './../../shared';

export class Estoque implements BaseEntity {
    constructor(
        public id?: number,
        public quantidade?: number,
        public produto?: BaseEntity,
    ) {
    }
}
