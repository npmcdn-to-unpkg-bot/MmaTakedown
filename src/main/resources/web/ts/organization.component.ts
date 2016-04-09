import {Component, OnInit, Input} from 'angular2/core';
import {MtdService} from './mtd.service.ts';
import {Organization} from './organization.ts';
import {EventComponent} from './event.component.ts';
import {Event} from './event.ts';
import { RouteParams } from 'angular2/router';


@Component({
    selector: 'organization',
    templateUrl: 'ts/organization.component.html',
    providers: [MtdService],
    directives: [EventComponent]
})

export class OrganizationComponent implements OnInit {
    @Input organization: Organization;

    events: Event[];

    constructor(private _service: MtdService, private _routeParams: RouteParams) {
    }


    ngOnInit() {
        let id = +this._routeParams.get('id');
        this._service.getOrganization(id).subscribe(res => this.organization = res);


        this._service.getEvents(id).subscribe(res => this.events = res.reverse());
    }
}
