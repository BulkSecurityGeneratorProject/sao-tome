/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaotomeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PedidoDetailComponent } from '../../../../../../main/webapp/app/entities/pedido/pedido-detail.component';
import { PedidoService } from '../../../../../../main/webapp/app/entities/pedido/pedido.service';
import { Pedido } from '../../../../../../main/webapp/app/entities/pedido/pedido.model';

describe('Component Tests', () => {

    describe('Pedido Management Detail Component', () => {
        let comp: PedidoDetailComponent;
        let fixture: ComponentFixture<PedidoDetailComponent>;
        let service: PedidoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaotomeTestModule],
                declarations: [PedidoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PedidoService,
                    JhiEventManager
                ]
            }).overrideTemplate(PedidoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PedidoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PedidoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pedido(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pedido).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
