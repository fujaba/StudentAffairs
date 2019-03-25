import { Yamler } from "@fujaba/fulib-yaml-ts";
import { ESEventListener } from './eventListener';
import { EventSource } from "./eventSource";
import { FileSystemListener } from 'apps/segroup-server/src/app/FileSystemListener';

export class EventSourceRegistry {
  private registry: Map<string, EventSource>;
  private eventSourceListener: ESEventListener;

  constructor(esListener: ESEventListener) {
    this.registry = new Map();
    this.eventSourceListener = esListener;
  }

  public appendEvent(event: Map<string, string>): EventSourceRegistry {
    // find the responsible eventSoure and forward the call
    const eventKey = event.get(EventSource.EVENT_KEY);
    const pos = eventKey.lastIndexOf('/');

    let eventSourceName = '_root';
    if (pos >= 0) {
      eventSourceName = eventKey.substring(0, pos);
    }

    let theEventSource = this.registry.get(eventSourceName);
    if (!theEventSource) {
      theEventSource = new EventSource();
      const localListener = this.eventSourceListener.createLocalListener(eventSourceName, theEventSource);
      (localListener as FileSystemListener).loadEventSourceFile();
      theEventSource.eventListener = localListener;
      this.registry.set(eventSourceName, theEventSource);
    }

    if ( ! theEventSource.isOverwritten(event)) {
      theEventSource.appendEvent(event);
    }

    return this;
  }
}