import SEGroup from "./SEGroup";
import { EventSource } from "./eventSource";
import { Yamler } from "@fujaba/fulib-yaml-ts";
import SEClass from './SEClass';
import { ESEventListener } from './eventListener';
import { stringify } from 'querystring';
import SEClassFolder from './SEClassFolder';

export class SEGroupBuilder {
  static BUILD_GROUP = 'buildGroup';
  static BUILD_CLASS_FOLDER = 'buildClassFolder';
  static BUILD_SE_CLASS = 'buildSEClass';
  static BUILD_ASSIGNMENT = 'buildAssignment';
  static BUILD_ACHIEVEMENT = 'buildAchievement';
  static BUILD_SOLUTION = 'buildSolution';
  static REMOVE_SE_CLASS = 'removeSEClass';
  static GRADE_SOLUTION = 'gradeSolution';

  static EVENT_KEY = '.eventKey';
  static EVENT_TYPE = 'eventType';
  static EVENT_TIMESTAMP = '.eventTimestamp';

  static THE_GROUP = 'theGroup';
  static HEAD = 'head';
  static STUDENT_CREATED = 'studentCreated';
  static STUDENT_ID = 'studentId';
  static NAME = 'name';
  static TOPIC = 'topic';
  static CURRENT_TERM = 'currentTerm';
  static TERM = 'term';
  static TASK = 'task';
  static POINTS = 'points';
  static STUDENT_ENROLLED = 'studentEnrolled';
  static COURSE_NAME = 'courseName';
  static LECTURER_NAME = 'lecturerName';
  static DATE = 'date';
  static GIT_URL = 'gitUrl';
  static GRADE = 'grade';
  static EXAMINATION_GRADED = 'examinationGraded';
  static STUDENT_HIRED_AS_TA = 'studentHiredAsTA';
  static TEACHING_ASSISTANT_FOR = 'teachingAssistantFor';

  constructor() {
    this.seGroup = new SEGroup();
    this.eventSource = new EventSource();
    this.loadEventStorage();
  }

  private seGroup: SEGroup;
  private eventSource: EventSource;

  public setEventListener(eventListener: ESEventListener) {
    this.eventSource.eventListener = eventListener;
  }

  private loadEventStorage() {
    console.log(`loading ${localStorage.length} events from localStorage...`);
    for (let i = 0; i < localStorage.length; i++) {
      let key = localStorage.key(i);
      let value = localStorage.getItem(key);
      console.log(key, value);
      this.eventSource.keepOriginalTimeStamp = true;
      this.applyEvents(value);
      this.eventSource.keepOriginalTimeStamp = false;
    }
    console.log('      ...done');
  }

  public applyEvents(yaml: string) {
    const yamler: Yamler = new Yamler();
    const list: Array<Map<string, string>> = yamler.decodeList(yaml);

    for (const map of list) {
      if (this.eventSource.isOverwritten(map)) continue;

      switch (map.get(EventSource.EVENT_TYPE)) {
        case SEGroupBuilder.BUILD_GROUP:
          {
            const name = map.get(SEGroupBuilder.NAME);
            const head = map.get(SEGroupBuilder.HEAD);
            const currentTerm = this.buildClassFolder(map.get(SEGroupBuilder.CURRENT_TERM));
            this.buildGroup(name, head, currentTerm);
            break;
          }
        case SEGroupBuilder.BUILD_CLASS_FOLDER:
          {
            const name = map.get(SEGroupBuilder.EVENT_KEY);
            const currentTerm = this.buildClassFolder(name);
            break;
          }
        case SEGroupBuilder.STUDENT_CREATED:
          break;
        case SEGroupBuilder.STUDENT_HIRED_AS_TA:
          break;
        case SEGroupBuilder.BUILD_SE_CLASS:
          {
            const topic = map.get(SEGroupBuilder.TOPIC);
            const key = map.get(EventSource.EVENT_KEY);
            const pos = key.lastIndexOf('/');
            let termName = '0000-00';
            if (pos >= 0) {
              termName = key.substring(0, pos);
            }
            const currentTerm = this.buildClassFolder(termName);
            this.buildSEClass(topic, currentTerm);
            break;
          }
        case SEGroupBuilder.REMOVE_SE_CLASS:
          {
            const topic = map.get(SEGroupBuilder.TOPIC);
            const term = map.get(SEGroupBuilder.TERM);
            this.removeSEClassByTopicTerm(topic, term);
            break;
          }
        case SEGroupBuilder.BUILD_ASSIGNMENT:
          break;
        case SEGroupBuilder.BUILD_ACHIEVEMENT:
          break;
        case SEGroupBuilder.STUDENT_ENROLLED:
          break;
        case SEGroupBuilder.BUILD_SOLUTION:
          break;
        case SEGroupBuilder.GRADE_SOLUTION:
          break;
        case SEGroupBuilder.EXAMINATION_GRADED:
          break;
        default:
          console.log(`Unsupported event type ${map.get(EventSource.EVENT_TYPE)}`);
          break;
      }
    }
  }



  public buildGroup(groupName: string, head: string, currentTerm: SEClassFolder): SEGroup {
    if (this.seGroup
      && this.seGroup.name === groupName
      && this.seGroup.head === head
      && this.seGroup.currentTerm === currentTerm)
      return this.seGroup;


    if (!this.seGroup) {
      this.seGroup = new SEGroup();
    }

    this.seGroup.name = groupName;
    this.seGroup.head = head;
    this.seGroup.currentTerm = currentTerm;

    // log event
    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.BUILD_GROUP);
    event.set(EventSource.EVENT_KEY, SEGroupBuilder.THE_GROUP);
    event.set(SEGroupBuilder.NAME, groupName);
    event.set(SEGroupBuilder.HEAD, head);
    event.set(SEGroupBuilder.CURRENT_TERM, currentTerm.name);

    this.getEventSource().appendEvent(event);

    return this.seGroup;
  }


  public buildClassFolder(termName: string): SEClassFolder {
    if (!this.seGroup) {
      this.seGroup = new SEGroup();
    }

    const folder = this.seGroup.getClassFolder(termName);

    if (folder) return folder; //<=======================

    const newFolder = new SEClassFolder();
    newFolder.name = termName;

    this.seGroup.withClassFolder(newFolder);

    // log event
    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.BUILD_CLASS_FOLDER);
    event.set(EventSource.EVENT_KEY, termName);

    this.getEventSource().appendEvent(event);

    return newFolder;
  }


  public buildSEClass(topic: string, currentTerm: SEClassFolder): SEClass {
    let myClass: SEClass = undefined;

    for (const c of currentTerm.classes) {
      if (topic === c.topic) {
        myClass = c;
        return myClass;
      }
    }

    myClass = new SEClass();
    myClass.topic = topic;
    myClass.term = currentTerm.name;
    currentTerm.withClasses(myClass);

    // log event
    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.BUILD_SE_CLASS);
    event.set(EventSource.EVENT_KEY, currentTerm.name + '/' + topic);
    event.set(SEGroupBuilder.TOPIC, topic);

    this.getEventSource().appendEvent(event);

    return myClass;
  }

  public removeSEClassByTopicTerm(topic: string, term: string): void {
    for (const seClass of this.seGroup.classes) {
      if (seClass.topic === topic && seClass.term === term) {
        this.removeSEClass(seClass);
      }
    }
  }

  public removeSEClass(seClass: SEClass): void {
    const pos = this.getSeGroup().classes.indexOf(seClass);
    if (pos < 0) {
      // we don't have it
      return;
    }

    this.getSeGroup().withoutClasses(seClass);

    // log event
    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.REMOVE_SE_CLASS);
    event.set(EventSource.EVENT_KEY, seClass.topic + seClass.term);
    event.set(SEGroupBuilder.TOPIC, seClass.topic);
    event.set(SEGroupBuilder.TERM, seClass.term);

    this.getEventSource().appendEvent(event);
  }


  public getSeGroup(): SEGroup {
    return this.seGroup;
  }

  public getEventSource(): EventSource {
    return this.eventSource;
  }
}
