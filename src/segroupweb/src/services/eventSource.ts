export class EventSource {
  EVENT_KEY = '.eventKey';
  EVENT_TIMESTAMP = '.eventTimestamp';
  EVENT_TYPE = 'eventType';
  
  // TODO: Yaml bib finden

  private keyNumMap: Map<string, number> = new Map<string, number>();
  private numEventMap: Map<number, Map<string, string>> = new Map<number, Map<string, string>>();
  private lastEventTime: number = -1;
  
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
  
  public append(event: Map<string, string>): EventSource {
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
  
  // TODO: Eventuell diese string builder append scheiße noch machen und die encode dinger
  
}