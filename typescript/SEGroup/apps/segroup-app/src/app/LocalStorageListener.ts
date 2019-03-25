import { ESEventListener } from '../../../../libs/segroup-model/src/lib/eventListener';
import { EventSource } from '../../../../libs/segroup-model/src/lib/eventSource';
import { SegroupService } from './segroup.service';

export class LocalStorageListener implements ESEventListener {
    private groupService: SegroupService;
    private eventSource: EventSource;

    constructor(gs: SegroupService, es: EventSource) {
        this.groupService = gs;
        this.eventSource = es;
    }

    createLocalListener(eventSourceName: string, theEventSource: any): ESEventListener {
        throw new Error("Method not implemented.");
    }


    public propertyChange(event: Map<string, string>) {
        const yaml = EventSource.encodeEvent(event);

        if (window.localStorage) {
            const key = event.get(EventSource.EVENT_KEY);
            localStorage.setItem(key, yaml);
        }

        // inform server
        this.groupService.uploadYaml(yaml);
    }
}
