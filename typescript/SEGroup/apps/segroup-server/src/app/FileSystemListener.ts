import { ESEventListener } from '../../../../libs/segroup-model/src/lib/eventListener';
import { EventSource } from '../../../../libs/segroup-model/src/lib/eventSource';
// const fs: any = {}; // require('fs');
import * as fs from 'fs';

export class FileSystemListener implements ESEventListener {
    private eventSourceName: string;
    private eventSource: EventSource;

    constructor(eventSourceName: string, es: EventSource) {
        this.eventSourceName = eventSourceName;
        this.eventSource = es;
    }

    public propertyChange(event: Map<string, string>) {
        const yaml = EventSource.encodeEvent(event);

        // append to file
        const dir = 'eventDB';
        if (!fs.existsSync(dir)){
            fs.mkdirSync(dir);
        }
        const fileName = 'eventDB/' + this.eventSourceName + '.yaml';
        fs.appendFile(fileName, yaml,  function(err) {
            if (err) {
                // try write
                return console.error(err);
            }
            // console.log("File created!");
        });

        // console.log('write file done');
    }

    
    public createLocalListener(eventSourceName: string, theEventSource: any) {
        return new FileSystemListener(eventSourceName, theEventSource as EventSource);
    }


    public loadEventSourceFile() {
        const fileName = 'eventDB/' + this.eventSourceName + '.yaml';
        if (fs.existsSync(fileName)) {
            const yaml = fs.readFileSync(fileName, 'utf8');
            this.eventSource.keepOriginalTimeStamp = true;
            this.eventSource.appendEvents(yaml);
            this.eventSource.keepOriginalTimeStamp = false;
        }
    }

}
