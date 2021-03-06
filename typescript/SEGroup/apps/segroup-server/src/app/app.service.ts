import { Injectable, HttpStatus } from '@nestjs/common';
import { Response } from 'express';
import { EventSource, SEGroupBuilder, EventSourceRegistry } from '@SEGroup/segroup-model';
import { Yamler } from '@fujaba/fulib-yaml-ts';
// import { InjectModel } from '@nestjs/mongoose';
// import { Model } from 'mongoose';
import { Event, ESEvent } from './event.interface';
import * as loki from "lokijs";
import { LookupOptions } from 'dns';
import { FileSystemListener } from './FileSystemListener';

@Injectable()
export class AppService {
  private yamler: Yamler;
  private eventSourceRegistry: EventSourceRegistry;
  private tmpES: EventSource; // just for reference, to be removed
  private lokiDB: loki.Loki;
  private lokiCollection: loki.Collection;

  constructor() {
    this.yamler = new Yamler();
    this.eventSourceRegistry = new EventSourceRegistry(new FileSystemListener(null, null));
    this.tmpES = new EventSource();
    const listener = new FileSystemListener('tmpES', this.tmpES);
    listener.loadEventSourceFile();
    this.tmpES.eventListener = listener;

    // this.lokiDB = new loki('seGroupLokiDB2019.json', {
    //   autoload: true,
    //   autoloadCallback: () => this.databaseInitialize(),
    //   autosave: true,
    //   autosaveInterval: 1000
    // });
    // console.log("created loki collection " + this.lokiCollection);
  }

  // databaseInitialize() {
  //   this.lokiCollection = this.lokiDB.getCollection("lokiEvents");
  //   if (this.lokiCollection === null) {
  //     this.lokiCollection = this.lokiDB.addCollection("lokiEvents");
  //   }
  // }

  async getSharedEvents(since: string, caller: string, res: Response) {
    // this.eventSourceRegistry;

    res.status(200).send('Hello World');
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

    // write to eventSourceRegistry
    for (let yamlEvent of eventList) {
      const eventKey = yamlEvent.get(SEGroupBuilder.EVENT_KEY);
      if ( ! this.tmpES.isOverwritten(yamlEvent))
      {
        this.tmpES.appendEvent(yamlEvent);
      }
      this.eventSourceRegistry.appendEvent(yamlEvent);
    }


    // // write to loki
    // for (let yamlEvent of eventList) {
    //   const dbEventList = this.lokiCollection.find({ eventKey: yamlEvent.get('.eventKey') })
    //   const yamlText = EventSource.encodeEvent(yamlEvent);
    //   if (!dbEventList || dbEventList.length === 0) {
        
    //     const dbEvent: ESEvent = {
    //       eventType: yamlEvent.get(SEGroupBuilder.EVENT_TYPE),
    //       eventKey: yamlEvent.get(SEGroupBuilder.EVENT_KEY),
    //       eventTimestamp: yamlEvent.get(SEGroupBuilder.EVENT_TIMESTAMP),
    //       parentKey: '42',
    //       yaml: yamlText
    //     }

    //     this.lokiCollection.insert(dbEvent);
    //     console.log('adding \n' + JSON.stringify(dbEvent));
    //   }
    //   else {
    //     const dbEvent = dbEventList[0];
    //     dbEvent.eventType = yamlEvent.get('eventType');
    //     dbEvent.eventKey = yamlEvent.get('.eventKey');
    //     dbEvent.eventTimestamp = yamlEvent.get('.eventTimestamp');
    //     dbEvent.parentKey = '42';
    //     dbEvent.yaml = yamlText;

    //     this.lokiCollection.update(dbEvent);
    //   }
    // }

    // this.lokiDB.save();


    // // write to mongo
    // for (let map of eventList) {
    //   const events = await this.eventModel.find({ eventKey: map.get('.eventKey') });
    //   const eventYaml = EventSource.encodeEvent(map);
    //   if (!events || events.length === 0) {
    //     const newEvent: Event = {
    //       eventType: map.get('eventType'),
    //       eventKey: map.get('.eventKey'),
    //       eventTimestamp: map.get('.eventTimestamp'),
    //       parentKey: '42',
    //       yaml: eventYaml
    //     }

    //     const createdEvent = new this.eventModel(newEvent);
    //     await createdEvent.save();
    //   } else {
    //     const event = events[0];
    //     event.eventType = map.get('eventType');
    //     event.eventKey = map.get('.eventKey');
    //     event.eventTimestamp = map.get('.eventTimestamp');
    //     event.parentKey = '42';
    //     event.yaml = eventYaml;

    //     await event.save();
    //   }
    // }

    res.status(HttpStatus.OK).send('OK');
  }
}
