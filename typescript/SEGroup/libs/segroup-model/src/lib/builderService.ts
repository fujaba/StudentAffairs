import SEGroup from "./SEGroup";
import { EventSource } from "./eventSource";
import { Yamler } from "@fujaba/fulib-yaml-ts";
import SEClass from './SEClass';
import { ESEventListener } from './eventListener';
import { stringify } from 'querystring';
import SEClassFolder from './SEClassFolder';
import Assignment from './Assignment';
import SEStudent from './SEStudent';
import Achievement from './Achievement';
import Solution from './Solution';

export class SEGroupBuilder {
  static BUILD_GROUP = 'buildGroup';
  static BUILD_CLASS_FOLDER = 'buildClassFolder';
  static BUILD_SE_CLASS = 'buildSEClass';
  static BUILD_ASSIGNMENT = 'buildAssignment';
  static BUILD_ACHIEVEMENT = 'buildAchievement';
  static BUILD_SOLUTION = 'buildSolution';
  static BUILD_STUDENT = 'buildStudent';
  static REMOVE_SE_CLASS = 'removeSEClass';
  static REMOVE_ASSIGNMENT = 'removeAssignment';
  static GRADE_SOLUTION = 'gradeSolution';

  static EVENT_KEY = '.eventKey';
  static EVENT_TYPE = 'eventType';
  static EVENT_TIMESTAMP = '.eventTimestamp';

  static THE_GROUP = 'theGroup';
  static HEAD = 'head';
  static STUDENT_CREATED = 'studentCreated';
  static STUDENT_ID = 'studentId';
  static NAME = 'name';
  static EMAIL = 'email';
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
        case SEGroupBuilder.BUILD_STUDENT:
          {
            const name = map.get(SEGroupBuilder.NAME);
            const studentId = map.get(SEGroupBuilder.EVENT_KEY);
            const email = map.get(SEGroupBuilder.EMAIL);
            const student = this.buildStudent(name, studentId, email);
            break;
          }
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
          {
            const task = map.get(SEGroupBuilder.TASK);
            const topic = map.get(SEGroupBuilder.TOPIC);
            const term = map.get(SEGroupBuilder.TERM);
            const seTerm = this.buildClassFolder(term);
            const seClass = this.buildSEClass(topic, seTerm);
            this.buildAssignment(task, seClass);
            break;
          }
        case SEGroupBuilder.REMOVE_ASSIGNMENT:
          {
            const task = map.get(SEGroupBuilder.TASK);
            const topic = map.get(SEGroupBuilder.TOPIC);
            const term = map.get(SEGroupBuilder.TERM);
            this.removeAssignmentByTaskTopicTerm(task, topic, term);
            break;
          }
        case SEGroupBuilder.BUILD_ACHIEVEMENT:
          {
            const task = map.get(SEGroupBuilder.NAME);
            const studentId = map.get(SEGroupBuilder.STUDENT_ID);
            const email = map.get(SEGroupBuilder.EMAIL);
            const topic = map.get(SEGroupBuilder.TOPIC);
            const term = map.get(SEGroupBuilder.TERM);
            const seTerm = this.buildClassFolder(term);
            const seClass = this.buildSEClass(topic, seTerm);
            this.buildAchievementByNameId(name, studentId, email, seClass);
            break;
          }
        case SEGroupBuilder.STUDENT_ENROLLED:
          break;
        case SEGroupBuilder.BUILD_SOLUTION:
          {
            const term = map.get(SEGroupBuilder.TERM);
            const seTerm = this.buildClassFolder(term);
            const topic = map.get(SEGroupBuilder.TOPIC);
            const seClass = this.buildSEClass(topic, seTerm);
            const studentId = map.get(SEGroupBuilder.STUDENT_ID);
            const student = this.buildStudentById(studentId);
            const task = map.get(SEGroupBuilder.TASK);
            const assignment = this.buildAssignment(task, seClass);
            const achievement = this.buildAchievement(student, seClass);
            const solution = this.buildSolution(assignment, achievement);
            break;
          }
        case SEGroupBuilder.GRADE_SOLUTION:
          {
            const term = map.get(SEGroupBuilder.TERM);
            const seTerm = this.buildClassFolder(term);
            const topic = map.get(SEGroupBuilder.TOPIC);
            const seClass = this.buildSEClass(topic, seTerm);
            const studentId = map.get(SEGroupBuilder.STUDENT_ID);
            const student = this.buildStudentById(studentId);
            const task = map.get(SEGroupBuilder.TASK);
            const assignment = this.buildAssignment(task, seClass);
            const achievement = this.buildAchievement(student, seClass);
            const solution = this.buildSolution(assignment, achievement);
            const points = map.get(SEGroupBuilder.POINTS);
            this.gradeSolution(solution, Number(points));
            break;
          }
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


  public buildStudentById(studentId: string) {
    for (const s of this.getSeGroup().students) {
      if (s.studentId === studentId) {
        return s;
      }
    }

    let student = new SEStudent();
    student.studentId = studentId;
    this.getSeGroup().withStudents(student);

    return student;
  }


  public buildStudent(name: string, studentId: string, email: string) {
    let student: SEStudent;

    for (const s of this.getSeGroup().students) {
      if (s.studentId === studentId) {
        student = s;
        break;
      }
    }

    if (student
      && student.name === name
      && (!email || student.email === email)) {
      return student;
    }

    if (!student) {
      student = new SEStudent();
      student.studentId = studentId;
      this.getSeGroup().withStudents(student);
    }

    student.name = name;
    student.email = email;

    // log event
    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.BUILD_STUDENT);
    event.set(EventSource.EVENT_KEY, student.studentId);
    event.set(SEGroupBuilder.NAME, name);
    if (email) {
      event.set(SEGroupBuilder.EMAIL, email);
    }

    this.getEventSource().appendEvent(event);

    return student;
  }


  public gradeSolution(solution: Solution, points: number) {
    if (solution.points === points) return;

    solution.points = points;

    // log event
    const assignment = solution.assignment;
    const achievement = solution.achievement;
    const seClass = assignment.seClass;
    const seTerm = seClass.folder;
    const student = achievement.student;

    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.GRADE_SOLUTION);
    event.set(EventSource.EVENT_KEY, seTerm.name + '/' + seClass.topic + '-' + assignment.task + '-' + student.studentId + '-' + points);
    event.set(SEGroupBuilder.TERM, seTerm.name);
    event.set(SEGroupBuilder.TOPIC, seClass.topic);
    event.set(SEGroupBuilder.TASK, assignment.task);
    event.set(SEGroupBuilder.STUDENT_ID, student.studentId);
    event.set(SEGroupBuilder.POINTS, `${points}`);

    this.getEventSource().appendEvent(event);
  }

  public buildSolution(assignment: Assignment, achievement: Achievement) {
    for (const s of assignment.solutions) {
      if (s.achievement === achievement) {
        return s;
      }
    }

    const solution = new Solution();
    solution.assignment = assignment;
    solution.achievement = achievement;

    // log event
    const seClass = assignment.seClass;
    const seTerm = seClass.folder;
    const student = achievement.student;

    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.BUILD_SOLUTION);
    event.set(EventSource.EVENT_KEY, seTerm.name + '/' + seClass.topic + '-' + assignment.task + '-' + student.studentId);
    event.set(SEGroupBuilder.TERM, seTerm.name);
    event.set(SEGroupBuilder.TOPIC, seClass.topic);
    event.set(SEGroupBuilder.TASK, assignment.task);
    event.set(SEGroupBuilder.STUDENT_ID, student.studentId);

    this.getEventSource().appendEvent(event);

    return solution;
  }


  public buildAchievementByNameId(name: string, studentId: string, email: string, seClass: SEClass) {
    const student = this.buildStudent(name, studentId, email);
    const achievement = this.buildAchievement(student, seClass);
    return achievement;
  }


  public buildAchievement(student: SEStudent, seClass: SEClass) {

    let achievement: Achievement;
    for (const a of seClass.participations) {
      if (a.student === student) {
        return a;
      }
    }

    achievement = new Achievement();
    achievement.student = student;
    achievement.seClass = seClass;

    // log event
    const seTerm = seClass.folder;

    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.BUILD_ACHIEVEMENT);
    event.set(EventSource.EVENT_KEY, seTerm.name + '/' + seClass.topic + '-' + student.studentId);
    event.set(SEGroupBuilder.NAME, student.name);
    event.set(SEGroupBuilder.STUDENT_ID, student.studentId);
    event.set(SEGroupBuilder.TOPIC, seClass.topic);
    event.set(SEGroupBuilder.TERM, seClass.folder.name);

    this.getEventSource().appendEvent(event);

    return achievement;
  }


  public buildAssignment(task: string, seClass: SEClass) {
    let myAssignment: Assignment = undefined;

    for (const a of seClass.assignments) {
      if (a.task === task) {
        myAssignment = a;
        return myAssignment;
      }
    }

    myAssignment = new Assignment();
    myAssignment.task = task;
    myAssignment.seClass = seClass;

    // log event
    const seTerm = seClass.folder;

    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.BUILD_ASSIGNMENT);
    event.set(EventSource.EVENT_KEY, seTerm.name + '/' + seClass.topic + '_' + task);
    event.set(SEGroupBuilder.TASK, task);
    event.set(SEGroupBuilder.TOPIC, seClass.topic);
    event.set(SEGroupBuilder.TERM, seTerm.name);

    this.getEventSource().appendEvent(event);

    return myAssignment;
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


  public removeAssignmentByTaskTopicTerm(task: string, topic: string, term: string): void {
    for (const seTerm of this.getSeGroup().classFolder) {
      if (seTerm.name === term) {
        for (const seClass of seTerm.classes) {
          if (seClass.topic === topic) {
            for (const assign of seClass.assignments) {
              if (assign.task === task) {
                this.removeAssignment(assign);
              }
            }
          }
        }
      }
    }
  }

  public removeAssignment(assign: Assignment) {
    if (!assign.seClass) return;


    // log event
    const task = assign.task;
    const seClass = assign.seClass;
    const seTerm = seClass.folder;
    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.REMOVE_ASSIGNMENT);
    event.set(EventSource.EVENT_KEY, seTerm.name + '/' + seClass.topic + '_' + task);
    event.set(SEGroupBuilder.TASK, task);
    event.set(SEGroupBuilder.TOPIC, seClass.topic);
    event.set(SEGroupBuilder.TERM, seTerm.name);

    assign.removeYou();

    this.getEventSource().appendEvent(event);
  }


  public removeSEClassByTopicTerm(topic: string, term: string): void {
    for (const seTerm of this.getSeGroup().classFolder) {
      if (seTerm.name === term) {
        for (const seClass of seTerm.classes) {
          if (seClass.topic === topic) {
            this.removeSEClass(seClass);
          }
        }
      }
    }
  }


  public removeSEClass(seClass: SEClass): void {
    if (!seClass.folder) return;


    // log event
    const seTerm = seClass.folder;
    const event = new Map<string, string>();
    event.set(EventSource.EVENT_TYPE, SEGroupBuilder.REMOVE_SE_CLASS);
    event.set(EventSource.EVENT_KEY, seTerm.name + '/' + seClass.topic);
    event.set(SEGroupBuilder.TOPIC, seClass.topic);

    seClass.removeYou();

    this.getEventSource().appendEvent(event);
  }



  public getSeGroup(): SEGroup {
    return this.seGroup;
  }

  public getEventSource(): EventSource {
    return this.eventSource;
  }
}
