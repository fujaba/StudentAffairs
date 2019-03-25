
import { EventSource } from './eventSource';

export interface ESEventListener {
    propertyChange(event: Map<string, string>) :void;

    createLocalListener(eventSourceName: string, theEventSource: EventSource): ESEventListener;

}
