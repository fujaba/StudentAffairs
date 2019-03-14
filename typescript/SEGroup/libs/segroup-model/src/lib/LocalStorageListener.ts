import { ESEventListener } from './eventListener';
import { EventSource } from './eventSource';

export class LocalStorageListener implements ESEventListener {
    private eventSource: EventSource;

    constructor(es: EventSource) {
        this.eventSource = es;
    }

    public propertyChange(event: Map<string, string>) {
        const yaml = EventSource.encodeEvent(event);

        if (window.localStorage) {
            const key = event.get(EventSource.EVENT_KEY);
            localStorage.setItem(key, yaml);

            for (let i = 0; i < localStorage.length; i++) {
                let key = localStorage.key(i);
                let value = localStorage.getItem(key);
                // console.log(key, value);
            }
        }
    }
}
