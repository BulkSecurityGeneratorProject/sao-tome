/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaotomeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ItemPedidoDetailComponent } from '../../../../../../main/webapp/app/entities/item-pedido/item-pedido-detail.component';
import { ItemPedidoService } from '../../../../../../main/webapp/app/entities/item-pedido/item-pedido.service';
import { ItemPedido } from '../../../../../../main/webapp/app/entities/item-pedido/item-pedido.model';

describe('Component Tests', () => {

    describe('ItemPedido Management Detail Component', () => {
        let comp: ItemPedidoDetailComponent;
        let fixture: ComponentFixture<ItemPedidoDetailComponent>;
        let service: ItemPedidoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaotomeTestModule],
                declarations: [ItemPedidoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ItemPedidoService,
                    JhiEventManager
                ]
            }).overrideTemplate(ItemPedidoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItemPedidoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemPedidoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ItemPedido(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.itemPedido).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
