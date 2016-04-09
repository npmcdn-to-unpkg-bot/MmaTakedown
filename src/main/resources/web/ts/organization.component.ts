import {Component, OnInit, Input} from 'angular2/core';
import {MtdService} from './mtd.service.ts';
import {Organization} from './organization.ts';
import { RouteParams } from 'angular2/router';


@Component({
    selector: 'organization',
    templateUrl: 'ts/organization.component.html',
    providers: [MtdService]
})

export class OrganizationComponent implements OnInit {
    @Input organization: Organization;

    constructor(private _service: MtdService, private _routeParams: RouteParams) {
    }


    ngOnInit() {
        let id = +this._routeParams.get('id');
        this._service.getOrganization(id).subscribe(res => this.organization = res);

    }
}
