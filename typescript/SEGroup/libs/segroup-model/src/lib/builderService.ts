import SEGroup from "./SEGroup";
import { EventSource } from "./eventSource";
import { Yamler } from "@fujaba/fulib-yaml-ts";
import SEClass from './SEClass';
import { LocalStorageListener } from './LocalStorageListener';
import { stringify } from 'querystring';

export class SEGroupBuilder {
  HEAD = 'head';
  BUILD_SE_GROUP = 'buildSEGroup';
  STUDENT_CREATED = 'studentCreated';
  STUDENT_ID = 'studentId';
  NAME = 'name';
  BUILD_SE_CLASS = 'buildSEClass';
  TOPIC = 'topic';
  TERM = 'term';
  TASK = 'task';
  POINTS = 'points';
  BUILD_ASSIGNMENT = 'buildAssignment';
  BUILD_ACHIEVEMENT = 'buildAchievement';
  STUDENT_ENROLLED = 'studentEnrolled';
  COURSE_NAME = 'courseName';
  LECTURER_NAME = 'lecturerName';
  DATE = 'date';
  BUILD_SOLUTION = 'buildSolution';
  GIT_URL = 'gitUrl';
  GRADE_SOLUTION = 'gradeSolution';
  GRADE = 'grade';
  EXAMINATION_GRADED = 'examinationGraded';
  STUDENT_HIRED_AS_TA = 'studentHiredAsTA';
  TEACHING_ASSISTANT_FOR = 'teachingAssistantFor';

  constructor() {
    this.seGroup = new SEGroup();
    this.eventSource = new EventSource();
    this.eventSource.eventListener = new LocalStorageListener(this.eventSource);
  }

  private seGroup: SEGroup;
  private eventSource: EventSource;

  public applyEvents(yaml: string) {
    const yamler: Yamler = new Yamler();
    const list: Array<Map<string, string>> = yamler.decodeList(yaml);

    for (const map of list) {
      switch (map.get(EventSource.EVENT_TYPE)) {
        case this.BUILD_SE_GROUP:
          this.buildSEGroup(map.get(this.HEAD));
          break;
        case this.STUDENT_CREATED:
          break;
        case this.STUDENT_HIRED_AS_TA:
          break;
        case this.BUILD_SE_CLASS:
          break;
        case this.BUILD_ASSIGNMENT:
          break;
        case this.BUILD_ACHIEVEMENT:
          break;
        case this.STUDENT_ENROLLED:
          break;
        case this.BUILD_SOLUTION:
          break;
        case this.GRADE_SOLUTION:
          break;
        case this.EXAMINATION_GRADED:
          break;
        default:
          console.log(`Unsupported event type ${map.get(EventSource.EVENT_TYPE)}`);
          break;
      }
    }
  }

  public buildSEGroup(head?: string): SEGroup {
    if (!head) throw new Error('Missing variable head in last event');

    if (this.seGroup && this.seGroup.head === head) return this.seGroup;

    if (!this.seGroup) {
      this.seGroup = new SEGroup();
    }

    this.seGroup.head = head;

    return this.seGroup;
  }

  public buildSEClass(topic: string, term: string): SEClass {
    let myClass: SEClass = undefined;

    for (const c of this.seGroup.classes) {
      if (topic + term === c.topic + c.term) {
        myClass = c;
        return myClass;
      }
    }

    myClass = new SEClass();
    myClass.topic = topic;
    myClass.term = term;
    this.seGroup.withClasses(myClass);

    // log event
    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, this.BUILD_SE_CLASS);
    event.set(EventSource.EVENT_KEY, topic + term);
    event.set(this.TOPIC, topic);
    event.set(this.TERM, term);

    this.getEventSource().appendEvent(event);

    return myClass;
  }

  public getSeGroup(): SEGroup {
    return this.seGroup;
  }

  public getEventSource(): EventSource {
    return this.eventSource;
  }
}
