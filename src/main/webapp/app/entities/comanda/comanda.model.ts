import { BaseEntity } from './../../shared';

export class Comanda implements BaseEntity {
    constructor(
        public id?: number,
        public identificador?: string,
        public disponivel?: boolean,
    ) {
        this.disponivel = false;
    }
}
