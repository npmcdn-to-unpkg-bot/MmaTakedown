import {Injectable} from 'angular2/core';
import {Http, HTTP_PROVIDERS, Headers} from 'angular2/http';
import 'rxjs/Rx';

import {Organization} from './organization';


@Injectable()
export class MtdService {
    postPutHeader: Headers;

    constructor(private _http: Http) {
        this.postPutHeader = new Headers();
        this.postPutHeader.append('Content-Type', 'application/json');

    }


    getOrganizations() {
        console.log("Get organizations");
        return this._http.get('api/organization').map(response => response.json());
    };

    getOrganization(id) {
        console.log("Get organization");
        return this._http.get('api/organization/' + id).map(response => response.json());
    };

    getEvents(id) {
        console.log("Get events");
        return this._http.get('api/organization/' + id + '/events').map(response => response.json());
    }

    getEventFights(id) {
        console.log("Get event fights");
        return this._http.get('api/event/' + id + '/fights').map(response => response.json());
    }

    updateEvent(event) {

        console.log("update event");
        return this._http.put('api/event', JSON.stringify(event), { headers: this.postPutHeader }).map(response => response.json());
    }

    refreshEvent(id) {
        console.log("refresh event");
        return this._http.get('refresh/event/' + id).timeout(10000).map(response => response.json());
    }

}