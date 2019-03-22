import { Document } from 'mongoose';

export interface Event extends Document {
    eventType: string,
    eventKey: string,
    eventTimestamp: string,
    parentKey: string, 
    yaml: string
};

export interface ESEvent {
    eventType: string,
    eventKey: string,
    eventTimestamp: string,
    parentKey: string, 
    yaml: string
};