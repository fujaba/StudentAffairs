import Yamler from "@fujaba/fulib-yaml-ts";

export class EventSource {
  EVENT_KEY = '.eventKey';
  EVENT_TIMESTAMP = '.eventTimestamp';
  EVENT_TYPE = 'eventType';
  private yamler: Yamler = new Yamler();
  
  private lastEventTime: number = -1;
  private keyNumMap: Map<string, number> = new Map<string, number>();
  private numEventMap: Map<number, Map<string, string>> = new Map<number, Map<string, string>>();
  
  public pull(since: number, relevantEventTypes?: string[]): Map<number, Map<string, string>> {
    const tailMap: Map<number, Map<string, string>> = new Map<number, Map<string, string>>();
    
    this.numEventMap.forEach((value: Map<string, string>, key: number) => {
      if (key >= since) {
        if (relevantEventTypes) {
          const type = value.get(this.EVENT_TYPE) || '';
          if (!relevantEventTypes.includes(type)) {
            return;
          }
        } 
        tailMap.set(key, value);
      }
    });
    
    return tailMap;  
  }
  
  public appendEvents(events: string): EventSource {
    const list: Array<Map<string, string>> = this.yamler.decodeList(events);  
    
    for (const event of list) {
      this.appendEvent(event);
    }
    
    return this;
  }
  
  public appendEvent(event: Map<string, string>): EventSource {
    this.lastEventTime = Date.now();
    const timestampString: string = '' + this.lastEventTime;
    
    event.set(this.EVENT_TIMESTAMP, timestampString);
    
    const key: string = event.get(this.EVENT_KEY) || '';
    if (key != '') {
      const oldNum: number = this.keyNumMap.get(key) || -1;
      if (oldNum > -1) this.numEventMap.delete(oldNum);
    }   
    
    this.keyNumMap.set(key, this.lastEventTime);
    this.numEventMap.set(this.lastEventTime, event);
    
    // TODO: Listener irgendwie machen und feuer
    
    return this;    
  }
  
  public encodeYaml(): string {
    return EventSource.encodeSortedEvents(this.numEventMap);
  }
  
  public static encodeSortedEvents(events: Map<number, Map<string, string>>): string {
    let result: string = '';
    
    events.forEach((value: Map<string, string>, key: number) => {
      result += this.encodeEvent(value);
    });
    
    return result;
  }
  
  public static encodeEvents(events: Array<Map<string, string>>): string {
    let result: string = '';
    
    for(const event of events) {
      result += this.encodeEvent(event);
    }
    
    return result;
  }
  
  public static encodeEvent(event: Map<string, string>): string {
    let result: string = '';
    let prefix: string = '- ';
    
    event.forEach((value: string, key: string) => {
      result += `${prefix}${key}: ${Yamler.encapsulate(value)}\n`;
      prefix = ' ';
    });
    result += '\n';
    
    return result;
  }
}