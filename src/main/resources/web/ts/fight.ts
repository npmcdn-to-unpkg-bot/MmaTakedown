import {Fighter} from './fighter.ts';
import {Event} from './event.ts';

export class Fight{
    id: number;
    event: Event;
    fighter1: Fighter;
    fighter2: Fighter;
    date: Date;
    result: number;
}