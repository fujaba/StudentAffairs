import { SEGroup } from "@/model";
import { EventSource } from "@/services/eventSource";

export class SEGroupBuilder {
  HEAD = 'head';
  BUILD_SE_GROUP = 'buildSEGroup';
  STUDENT_CREATED = 'studentCreated';
  EVENT_TYPE = 'eventType';
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
  
  private seGroup: SEGroup = {};
  private eventSource: EventSource = new EventSource();
  
  public applyEvents(yaml: string) {
      // TODO:
  }
  
  
  
  
  public getSeGroup(): SEGroup {
    return this.seGroup;
  }
  
  public getEventSource(): EventSource {
    return this.eventSource;
  }
}