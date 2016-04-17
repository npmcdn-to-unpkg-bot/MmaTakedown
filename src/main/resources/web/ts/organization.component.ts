import {Component, OnInit, Input, DoCheck} from 'angular2/core';
import {MtdService} from './mtd.service.ts';
import {Organization} from './organization.ts';
import {EventComponent} from './event.component.ts';
import {Event} from './event.ts';
import {Router, RouteParams } from 'angular2/router';


@Component({
    selector: 'organization',
    templateUrl: 'ts/organization.component.html',
    styleUrls: ['css/organization.css'],
    providers: [MtdService],
    directives: [EventComponent]
})

export class OrganizationComponent implements OnInit, DoCheck {
    @Input organization: Organization;

    events: Event[];

    update: boolean;

    constructor(private _service: MtdService, private _routeParams: RouteParams, private _router: Router) {
        this.update = false;
    }


   

    ngOnInit() {
        let id = +this._routeParams.get('id');
        this._service.getOrganization(id).subscribe(res => {
            this.organization = res
            console.log('Loaded organization:');
            console.log(this.organization);
        });
        this._service.getEvents(id).subscribe(res => this.events = res.reverse());
    }
    
    goToEvent(event){
        let link = ['Event', {id: event.id}];
        this._router.navigate(link);    
    }
    
    
    follow(value){
        this.organization.following = value;    
        this.updateOrganization();
    }
    
    followPrelims(value){
        this.organization.getPrelims = value;  
        this.updateOrganization();
    }
    
    changeQuality(value){
        this.organization.quality = value;    
        this.updateOrganization();
    }

    updateOrganization(){
        console.log(this.organization);
       this._service.updateOrganization(this.organization).subscribe(res => this.organization = res);
    }
}
