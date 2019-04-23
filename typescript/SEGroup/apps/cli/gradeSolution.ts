
// const EventSource = require( '../../libs/segroup-model/src/lib/eventSource');
// const SEGroupBuilder = require('../../libs/segroup-model/src/lib/builderService';

import { EventSource as es } from '../../libs/segroup-model/src/lib/eventSource';
import { SEGroupBuilder as gb } from '../../libs/segroup-model/src/lib/builderService';

const points = process.argv[2];
console.log(`Uploading ${points} for lennart scenarios pm 2019-10`);

const term = '2019-10'; 
const topic = 'PM';
const task = 'Scenarios';
const studentId = 'm1337';

const theEvent: Map<string, string> = new Map<string, string>();
theEvent.set(es.EVENT_TYPE, gb.GRADE_SOLUTION);
theEvent.set(es.EVENT_KEY, term + '/' + topic + '-' + task + '-' + studentId + '-' + points);
theEvent.set(gb.TERM, term);
theEvent.set(gb.TOPIC, topic);
theEvent.set(gb.TASK, task);
theEvent.set(gb.STUDENT_ID, studentId);
theEvent.set(gb.POINTS, points);

const yaml = es.encodeEvent(theEvent);

console.log(yaml);

