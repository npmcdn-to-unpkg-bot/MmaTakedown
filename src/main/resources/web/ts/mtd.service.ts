import {Injectable} from 'angular2/core';
import {Http, HTTP_PROVIDERS} from 'angular2/http';
import 'rxjs/Rx';

import {Organization} from './organization';


@Injectable()
export class MtdService{

    constructor(private _http:Http) { }


    getOrganizations(){
        return this._http.get('api/organization').map(response => response.json());
     };

    getOrganization(id){
        return this._http.get('api/organization/'+id).map(response => response.json());
     };

    
      
}