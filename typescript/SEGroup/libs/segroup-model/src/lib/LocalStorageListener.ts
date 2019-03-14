import { ESEventListener } from './eventListener';
import { EventSource } from './eventSource';

export class LocalStorageListener implements ESEventListener {
    private eventSource: EventSource;

    constructor(es: EventSource) {
        this.eventSource = es;
    }

    public propertyChange(event: Map<string, string>) {
       const yaml = EventSource.encodeEvent(event);
       console.log('logging: \n' + yaml);
    }
}
