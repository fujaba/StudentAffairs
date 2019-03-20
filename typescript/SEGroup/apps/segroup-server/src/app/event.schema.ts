import * as mongoose from 'mongoose';

export const EventSchema = new mongoose.Schema({
    eventType: String,
    eventKey: String,
    eventTimestamp: String,
    parentKey: String, 
    yaml: String
});

EventSchema.index({eventKey: 1});
EventSchema.index({parentKey: 1});
EventSchema.index({eventTimestamp: 1});
EventSchema.index({eventType: 1});
