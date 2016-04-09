import {Component, OnInit, Input} from 'angular2/core';
import {MtdService} from './mtd.service.ts';
import {Event} from './event.ts';
import { RouteParams } from 'angular2/router';
import {Fight} from './fight.ts';

@Component({
    selector: 'fight',
    styleUrls: ['css/fight.css'],
    templateUrl: 'ts/fight.component.html',
    providers: [MtdService]
})

export class FightComponent implements OnInit {
    @Input() fight: fight;
    showDetails: boolean = false;
    fights: Fight[];
    
    
    constructor(private _service: MtdService) {
    }


    ngOnInit() {

    }
    
    toggle(){
       
    }
}
