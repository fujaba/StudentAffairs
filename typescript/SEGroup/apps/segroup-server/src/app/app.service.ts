import { Injectable, HttpStatus } from '@nestjs/common';
import { Response } from 'express';
import { EventSource } from '@SEGroup/segroup-model';
import { Yamler } from '@fujaba/fulib-yaml-ts';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { Event, ESEvent } from './event.interface';
import * as loki from "lokijs";
import { LookupOptions } from 'dns';

@Injectable()
export class AppService {
  private yamler: Yamler;
  private file2EventSourceMap: Map<string,EventSource>;
  private lokiDB: loki.Loki;
  private lokiCollection: loki.Collection;

  constructor(@InjectModel('Event') private readonly eventModel: Model<Event>) {
    this.yamler = new Yamler();
    this.file2EventSourceMap = new Map();
    this.lokiDB = new loki('seGroupLokiDB2019.json', {
      autoload: true,
      autoloadCallback: () => this.databaseInitialize(),
      autosave: true,
      autosaveInterval: 1000
    });
    // console.log("created loki collection " + this.lokiCollection);
  }

  databaseInitialize() {
    this.lokiCollection = this.lokiDB.getCollection("lokiEvents");
    if (this.lokiCollection === null) {
      this.lokiCollection = this.lokiDB.addCollection("lokiEvents");
    }
  }

  async getSharedEvents(since: string, caller: string, res: Response) {
    const events: Event[] = await this.eventModel.find({});

    let yaml = "";
    for (const elem of events) {
      const tmp = elem.yaml;
      yaml = yaml + tmp + '\n';
    }

    const json = { yaml: `${yaml}` };
    const text = JSON.stringify(json);

    res.status(200).send(text);
  }

  async putSharedEvents(caller: string, body: any, res: Response) {

    if (!body) {
      res.status(400).send('Body is required');
    }

    const yaml = body.yaml;

    if (!yaml) {
      console.log('Ups, putShared got wrong body \n'
        + 'caller ' + caller + '\n'
        + JSON.stringify(body));
      return;
    }
    else {
      // console.log('Great, putShared got body \n' + body.yaml);
    }

    const eventList = this.yamler.decodeList(yaml);

    // write to file
    for (let yamlEvent of eventList) {
    }


    // write to loki
    for (let yamlEvent of eventList) {
      const dbEventList = this.lokiCollection.find({ eventKey: yamlEvent.get('.eventKey') })
      const yamlText = EventSource.encodeEvent(yamlEvent);
      if (!dbEventList || dbEventList.length === 0) {
        const dbEvent: ESEvent = {
          eventType: yamlEvent.get('eventType'),
          eventKey: yamlEvent.get('.eventKey'),
          eventTimestamp: yamlEvent.get('.eventTimestamp'),
          parentKey: '42',
          yaml: yamlText
        }

        this.lokiCollection.insert(dbEvent);
        console.log('adding \n' + JSON.stringify(dbEvent));
      }
      else {
        const dbEvent = dbEventList[0];
        dbEvent.eventType = yamlEvent.get('eventType');
        dbEvent.eventKey = yamlEvent.get('.eventKey');
        dbEvent.eventTimestamp = yamlEvent.get('.eventTimestamp');
        dbEvent.parentKey = '42';
        dbEvent.yaml = yamlText;

        this.lokiCollection.update(dbEvent);
      }
    }

    this.lokiDB.save();


    // write to mongo
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
