import {Component, OnInit} from 'angular2/core';
import {MtdService} from './mtd.service.ts';
import {Organization} from './organization.ts';
import {OrganizationComponent} from './organization.component.ts';
import { Router, RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';


@Component({
    selector: 'organizations',
    templateUrl: 'ts/organizations.component.html',
    styleUrls: ['css/organizations.css'],
    directives: [ROUTER_DIRECTIVES],
    providers: [ROUTER_PROVIDERS, MtdService]
})
@RouteConfig([
    {
        path: '/organization/:id',
        name: 'Organization',
        component: OrganizationComponent
    },
])

export class OrganizationsComponent implements OnInit {
    title = 'Mma Takedown';
    organizations: Organization[];

    constructor(private _service: MtdService, private _router: Router) {
    }


    ngOnInit() {
        this._service.getOrganizations().subscribe(res => this.organizations = res);
    }
    
    goToOrganization(organization: Organization){
        let link = ['Organization', {id: organization.id}];
        this._router.navigate(link);    
    }
}
