import { Injectable, HttpStatus } from '@nestjs/common';
import { Response } from 'express';
import { EventSource } from '@SEGroup/segroup-model';
import { Yamler } from '@fujaba/fulib-yaml-ts';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { Event } from './event.interface';

@Injectable()
export class AppService {
  private yamler: Yamler;

  constructor(@InjectModel('Event') private readonly eventModel: Model<Event>) {
    this.yamler = new Yamler();
  }

  async getSharedEvents(since: string, caller: string, res: Response) {
    const events: Event[] = await this.eventModel.find({ });

    let yaml = "";
    for (const elem of events)
    {
      const tmp = elem.yaml;
      yaml = yaml + tmp + '\n';
    }

    const json = { yaml: `${yaml}`};
    const text = JSON.stringify(json);

    res.status(200).send(text);
  }

  async putSharedEvents(caller: string, body: any, res: Response) {

    if (!body) {
      res.status(400).send('Body is required');
    }

    const yaml = body.yaml;

    if ( ! yaml) {
      console.log('Ups, putShared got wrong body \n' 
        + 'caller ' + caller + '\n'
        + JSON.stringify(body));
      return; 
    }
    else
    {
      console.log('Great, putShared got body \n' + body.yaml);
    }

    const eventList = this.yamler.decodeList(yaml);
    
    for (let map of eventList) {
      const events = await this.eventModel.find({ eventKey: map.get('.eventKey') });
      const eventYaml = EventSource.encodeEvent(map);
      if (!events || events.length === 0) { 
        const newEvent: Event = {
          eventType: map.get('eventType'),
          eventKey: map.get('.eventKey'),
          eventTimestamp: map.get('.eventTimestamp'),
          parentKey: '42',
          yaml: eventYaml
        }

        const createdEvent = new this.eventModel(newEvent);
        await createdEvent.save();
      } else {
        const event = events[0];
        event.eventType = map.get('eventType');
        event.eventKey = map.get('.eventKey');
        event.eventTimestamp = map.get('.eventTimestamp');
        event.parentKey = '42';
        event.yaml = eventYaml;

        await event.save();
      }
    }

    res.status(HttpStatus.OK).send('OK');
  }
}
