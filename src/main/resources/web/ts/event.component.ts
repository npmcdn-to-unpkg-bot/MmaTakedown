import {Component, OnInit, Input} from 'angular2/core';
import {MtdService} from './mtd.service.ts';
import {Organization} from './organization.ts';
import {Event} from './event.ts';
import { RouteParams } from 'angular2/router';
import {Fight} from './fight.ts';
import {FightComponent} from './fight.component.ts';


@Component({
    selector: 'event',
    styleUrls: ['css/event.css'],
    templateUrl: 'ts/event.component.html',
    directives: [FightComponent],
    providers: [MtdService]
})

export class EventComponent implements OnInit {
    @Input() event: Event;
    showDetails: boolean = false;
    fights: Fight[];


    constructor(private _service: MtdService) {
    }


    ngOnInit() {

    }

    toggle() {
        this.showDetails = !this.showDetails;
        if (this.fights === undefined || this.fights.length === 0) {
            this._service.getEventFights(this.event.id).subscribe(res => this.fights = res);
        }
    }

    getStatusLabel(status) {
        switch (status) {
            case 0:
                return 'skipped';
            case 1:
                return 'wanted';
            case 2:
                return 'snatched';
            case 3:
                return 'available';
        }
    }

    statusChanged(newValue) {
        if (newValue > -1) {
            console.log('hello' + newValue);
            this.event.status = newValue;
            this._service.updateEvent(this.event).subscribe(res => this.event = res);
            console.log(this.event);
        }
    }

    prelimStatusChanged(newValue) {
        if (newValue > -1) {
            this.event.prelimStatus = newValue;
            this._service.updateEvent(this.event).subscribe(res => this.event = res);
        }
    }

    earlyPrelimStatusChanged(newValue) {
        if (newValue > -1) {
            this.event.earlyPrelimStatus = newValue;
            this._service.updateEvent(this.event).subscribe(res => this.event = res);
        }
    }

    refreshEvent() {
        this._service.refreshEvent(this.event.id).subscribe(res =>{
            this.event = res
            this._service.getEventFights(this.event.id).subscribe(res => this.fights = res)
        });

    }
}
