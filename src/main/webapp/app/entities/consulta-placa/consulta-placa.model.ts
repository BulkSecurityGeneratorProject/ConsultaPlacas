import { BaseEntity } from './../../shared';

export class ConsultaPlaca implements BaseEntity {
    constructor(
        public id?: number,
        public issi?: string,
        public responsable?: string,
        public fecha?: any,
        public consulta?: string,
        public metodo?: string,
        public estado?: boolean,
        public resultado?: string,
        public municipioId?: number,
        public corporacionId?: number,
    ) {
        this.estado = false;
    }
}
