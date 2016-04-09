import {Organization} from './organization.ts';

export class Event{
    id:number;
    status: number;
    prelimStatus: number;
    earlyPrelimStatus: number;
    organization: Organization;
    name: string;
    date: Date;
    sherdogUrl: string;
    originalName: string;
    location: string;
    prelimLocation: string;
    earlyPrelimLocation: string;
}