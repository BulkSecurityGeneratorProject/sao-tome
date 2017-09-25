/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaotomeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EstoqueDetailComponent } from '../../../../../../main/webapp/app/entities/estoque/estoque-detail.component';
import { EstoqueService } from '../../../../../../main/webapp/app/entities/estoque/estoque.service';
import { Estoque } from '../../../../../../main/webapp/app/entities/estoque/estoque.model';

describe('Component Tests', () => {

    describe('Estoque Management Detail Component', () => {
        let comp: EstoqueDetailComponent;
        let fixture: ComponentFixture<EstoqueDetailComponent>;
        let service: EstoqueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaotomeTestModule],
                declarations: [EstoqueDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EstoqueService,
                    JhiEventManager
                ]
            }).overrideTemplate(EstoqueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstoqueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstoqueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Estoque(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.estoque).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
