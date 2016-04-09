import {bootstrap}    from 'angular2/platform/browser';
import {OrganizationsComponent} from './organizations.component.ts';
import { HTTP_PROVIDERS } from 'angular2/http';
import { RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';

bootstrap(OrganizationsComponent, [ HTTP_PROVIDERS, ROUTER_DIRECTIVES, ROUTER_PROVIDERS]);
